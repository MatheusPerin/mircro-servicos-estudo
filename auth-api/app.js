import express from "express";
import * as db from "./src/config/db/initialData.js";
import userRouters from "./src/modules/routes/userRoutes.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

db.createInitialData();

app.use(express.json());

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth-API',
        status: 'UP',
        httpStatus: 200
    })
});

app.use(userRouters);

app.listen(PORT, console.info('Server iniciado na porta', PORT))