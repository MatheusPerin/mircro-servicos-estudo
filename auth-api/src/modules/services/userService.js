import userRespository from "../repositories/userRespository.js";
import * as httpStatus from "../../config/constants/httpStatus.js";
import UserException from "../exceptions/userException.js";
import jwt from "jsonwebtoken";
import * as apiSecret from "../../config/constants/secret.js";

class UserService {

    async findByEmail(req) {
        try {
            const {email} = req.params;
            const {authUser} = req;

            this.validateEmail(email);

            let user = await userRespository.findByEmail(email);

            this.validateAuthenticatedUser(user, authUser)

            this.validateUserNotFound(user);

            return {
                status: httpStatus.SUCCES,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email,
                },
            }
        } catch (error) {
            return {
                status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.message,
            }
        }
    }

    async getAccessToken(req) {
        try {
            const {email, password} = req.body;

            this.validateAccessTokenData(email, password);

            let user = await userRespository.findByEmail(email);

            this.validateUserNotFound(user)

            await this.validatePassword(password, user.password);

            const authUser = {
                id: user.id,
                name: user.name,
                email: user.email,
            }

            const accessToken = jwt.sign({authUser}, apiSecret.API_SECRET, {expiresIn: "1d"});

            return {
                status: httpStatus.SUCCES,
                accessToken: accessToken,
            }
        } catch (error) {
            return {
                status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.message,
            }
        }
    }

    validateAccessTokenData(email, password) {
        if (!email || !password) {
            throw new UserException(httpStatus.UNAUTHORIZED, "Email and password must be informed")
        }
    }

    validateEmail(email) {
        if (!email) {
            throw new UserException(httpStatus.BAD_REQUEST, 'Email required')
        }
    }

    validateUserNotFound(user) {
        if (!user) {
            throw new UserException(httpStatus.BAD_REQUEST, 'User not found')
        }
    }
    
    validateAuthenticatedUser(user, authUser) {
        if (!authUser) return;

        if (user.id !== authUser.id) 
            throw new UserException(httpStatus.FORBIDDEN, 'Without permission')
    }

    async validatePassword(password, userPassword) {
        let compare = password === userPassword;

        if (!compare) {
            throw new UserException(httpStatus.UNAUTHORIZED, 'Access denied')
        }
    }

}

export default new UserService();