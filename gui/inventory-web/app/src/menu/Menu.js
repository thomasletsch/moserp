import MenuController from 'menu/MenuController'

let moduleName = angular
    .module("menu", [])
    .controller("MenuController", MenuController)
    .directive('appMenu', function () {
        return {
            templateUrl: 'src/menu/view/menu.html',
            controller: MenuController
        };
    })
    .name;

export default moduleName;
