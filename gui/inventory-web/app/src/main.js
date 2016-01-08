import registry from 'registry/Registry';
import structure from 'structure/Structure';
import authentication from 'authentication/Authentication';
import entities from 'entities/Entities';
import homeController from 'home/HomeController'
import { ExternalLogger } from 'utils/LogDecorator';

let $log = new ExternalLogger();
$log = $log.getInstance( "BOOTSTRAP" );
$log.debug( "Configuring 'main' module" );

export default angular.module('main', [registry, structure, authentication, entities] ).name;
