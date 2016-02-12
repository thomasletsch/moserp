System.config({
  defaultJSExtensions: true,
  transpiler: "traceur",
  paths: {
    "utils/*": "src/utils/*.js",
    "app/*": "src/*.js",
    "entities/*": "src/entities/*.js",
    "authentication/*": "src/authentication/*.js",
    "registry/*": "src/registry/*.js",
    "structure/*": "src/structure/*.js",
    "home/*": "src/home/*.js",
    "menu/*": "src/menu/*.js",
    "github:*": "jspm_packages/github/*",
    "npm:*": "jspm_packages/npm/*"
  },

  map: {
    "angular": "github:angular/bower-angular@1.4.8",
    "angular-animate": "github:angular/bower-angular-animate@1.4.8",
    "angular-aria": "github:angular/bower-angular-aria@1.4.8",
    "angular-cookies": "npm:angular-cookies@1.4.8",
    "angular-messages": "github:angular/bower-angular-messages@1.4.8",
    "angular-route": "github:angular/bower-angular-route@master",
    "angular-schema-form": "npm:angular-schema-form@0.8.12",
    "angular-schema-form-bootstrap": "npm:angular-schema-form-bootstrap@0.2.0",
    "angular-translate": "github:angular-translate/bower-angular-translate@2.8.1",
    "angular-translate-loader-static-files": "github:angular-translate/bower-angular-translate-loader-static-files@2.8.1",
    "angular-ui-bootstrap": "npm:angular-ui-bootstrap@0.14.3",
    "angular-ui-grid": "github:angular-ui/bower-ui-grid@3.0.7",
    "angular-ui-router": "github:angular-ui/ui-router@0.2.15",
    "bootstrap": "npm:bootstrap@3.3.6",
    "css": "github:systemjs/plugin-css@0.1.20",
    "jquery": "npm:jquery@2.1.4",
    "json": "github:systemjs/plugin-json@0.1.0",
    "ngstorage": "npm:ngstorage@0.3.10",
    "oauth-ng": "npm:oauth-ng@0.4.5",
    "query-string": "npm:query-string@3.0.0",
    "text": "github:systemjs/plugin-text@0.0.2",
    "traceur": "github:jmcriffey/bower-traceur@0.0.93",
    "traceur-runtime": "github:jmcriffey/bower-traceur-runtime@0.0.93",
    "github:angular-translate/bower-angular-translate-loader-static-files@2.8.1": {
      "angular-translate": "github:angular-translate/bower-angular-translate@2.8.1"
    },
    "github:angular-translate/bower-angular-translate@2.8.1": {
      "angular": "github:angular/bower-angular@1.4.8"
    },
    "github:angular-ui/ui-router@0.2.15": {
      "angular": "github:angular/bower-angular@1.4.8"
    },
    "github:angular/bower-angular-animate@1.4.8": {
      "angular": "github:angular/bower-angular@1.4.8"
    },
    "github:angular/bower-angular-aria@1.4.8": {
      "angular": "github:angular/bower-angular@1.4.8"
    },
    "github:jspm/nodelibs-assert@0.1.0": {
      "assert": "npm:assert@1.3.0"
    },
    "github:jspm/nodelibs-path@0.1.0": {
      "path-browserify": "npm:path-browserify@0.0.0"
    },
    "github:jspm/nodelibs-process@0.1.2": {
      "process": "npm:process@0.11.2"
    },
    "github:jspm/nodelibs-util@0.1.0": {
      "util": "npm:util@0.10.3"
    },
    "npm:angular-schema-form-bootstrap@0.2.0": {
      "fs": "github:jspm/nodelibs-fs@0.1.2"
    },
    "npm:angular-schema-form@0.8.12": {
      "angular": "npm:angular@1.4.8",
      "angular-sanitize": "npm:angular-sanitize@1.4.8",
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "objectpath": "npm:objectpath@1.2.1",
      "process": "github:jspm/nodelibs-process@0.1.2",
      "tv4": "npm:tv4@1.0.18"
    },
    "npm:angular-ui-bootstrap@0.14.3": {
      "angular": "npm:angular@1.4.8"
    },
    "npm:angular@1.4.8": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:assert@1.3.0": {
      "util": "npm:util@0.10.3"
    },
    "npm:bootstrap@3.3.6": {
      "fs": "github:jspm/nodelibs-fs@0.1.2",
      "path": "github:jspm/nodelibs-path@0.1.0",
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:inherits@2.0.1": {
      "util": "github:jspm/nodelibs-util@0.1.0"
    },
    "npm:oauth-ng@0.4.5": {
      "process": "github:jspm/nodelibs-process@0.1.2",
      "systemjs-json": "github:systemjs/plugin-json@0.1.0"
    },
    "npm:path-browserify@0.0.0": {
      "process": "github:jspm/nodelibs-process@0.1.2"
    },
    "npm:process@0.11.2": {
      "assert": "github:jspm/nodelibs-assert@0.1.0"
    },
    "npm:query-string@3.0.0": {
      "strict-uri-encode": "npm:strict-uri-encode@1.1.0"
    },
    "npm:util@0.10.3": {
      "inherits": "npm:inherits@2.0.1",
      "process": "github:jspm/nodelibs-process@0.1.2"
    }
  }
});
