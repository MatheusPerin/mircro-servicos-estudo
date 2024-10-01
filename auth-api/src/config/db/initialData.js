import User from "../../modules/user/model/user.js";

export async function createInitialData() {
    try {
        await User.sync({force: true});

        let password = '123456';

        await User.create({
            name: 'test',
            email: 'test@email.com',
            password: password,
        })

        await User.create({
            name: 'test 1',
            email: 'test1@email.com',
            password: password,
        })
    } catch (error) {
        console.error('Error persisting:', error.message);
    }
}