function RegistryService(EurekaClient) {
    return {
        /**
         * @return {string}
         */
        LoginUrl: function () {
            return (EurekaClient.getInstanceById('ENVIRONMENT.MOSERP.ORG') + '/login');
        }
    }
}

export default ['EurekaClient', RegistryService];
