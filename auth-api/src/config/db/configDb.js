import { Sequelize } from "sequelize";

const sequelize = new Sequelize(
    "auth-db", 
    "postgres", 
    "1604",
    {
        host: "localhost",
        dialect: "postgres",
        quoteIdentifiers: false,
        define: {
            syncOnAssociation: true,
            timestamps: false,
            underscored: true,
            underscoredAll: true,
            freezeTableName: true
        }
    }
);

sequelize
    .authenticate()
    .then(() => console.info('Connected to the database'))
    .catch((error) => console.error('Failed to connect to database', error.message));

export default sequelize;