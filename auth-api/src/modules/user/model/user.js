import { INTEGER, Sequelize, STRING } from "sequelize";

import sequelize from "../../../config/db/configDb.js";

const User = sequelize.define(
    "user",
    {
        id: {
            type: INTEGER,
            primaryKey: true,
            autoIncrement: true
        },
        name: {
            type: STRING,
            allowNull: false
        },
        email: {
            type: STRING,
            allowNull: false
        },
        password: {
            type: STRING,
            allowNull: false
        }
    },
    {}
);

export default User;