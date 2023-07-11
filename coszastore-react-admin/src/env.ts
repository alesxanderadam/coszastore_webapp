export class EnvVariable {
    static values: {
        publicUrl: string;
    }

    static getEnv(): {
        publicUrl: string;
    } {
        if (EnvVariable.values) {
            return EnvVariable.values;
        }

        EnvVariable.values = {
            publicUrl: process.env.PUBLIC_URL
        }

        return EnvVariable.values;
    }
}