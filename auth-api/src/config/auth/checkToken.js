import jwt from "jsonwebtoken";
import {promisify} from "util"
import * as apiSecret from "../constants/secret.js";
import CheckTokenException from "./checkTokenException.js";
import * as httpStatus from "../constants/httpStatus.js";

const BEARER = "bearer ";

export default async (req, res, next) => {
    try {
        let {authorization} = req.headers;

        if (!authorization) {
            throw new CheckTokenException(httpStatus.UNAUTHORIZED, 'Access denied')
        }

        if (authorization.toLowerCase().includes(BEARER)) {
            authorization = authorization.replace(BEARER, "");
        }

        const decoded = await promisify(jwt.verify)(authorization, apiSecret.API_SECRET);

        req.authUser = {...decoded.authUser};
        
        return next();
    } catch (error) {
        const status = error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR;
        return res.status(status).json({
            status: status,
            message: error.message,
        });
    }
};