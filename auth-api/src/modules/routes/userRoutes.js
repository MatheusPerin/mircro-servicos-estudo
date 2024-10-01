import { Router } from "express";
import userController from "../controllers/userController.js";
import checkToken from "../../config/auth/checkToken.js";

const userRouters = new Router();

userRouters.post('/api/user/auth', userController.getAccessToken);

userRouters.use(checkToken);

userRouters.get('/api/user/email/:email', userController.findByEmail);

export default userRouters;