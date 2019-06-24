(defproject district0x/district-server-db "1.0.5-SNAPSHOT"
  :description "district0x server module for setting up database connection"
  :url "https://github.com/district0x/district-server-db"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[district0x/district-server-config "1.0.1"]
                 [honeysql "0.9.4"]
                 [mount "0.1.16"]
                 [nilenso/honeysql-postgres "0.2.5" :exclusions [honeysql]]
                 [org.clojure/clojurescript "1.10.520"]]

  :npm {:dependencies [[better-sqlite3 "5.4.0"]]
        :devDependencies [[ws "2.0.1"]]}

  :figwheel {:server-port 4678}

  :clean-targets ^{:protect false} ["tests-compiled" "target"]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.0"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [figwheel-sidecar "0.5.18"]
                                  [org.clojure/tools.nrepl "0.2.13"]]
                   :plugins [[lein-ancient "0.6.15"]
                             [lein-cljsbuild "1.1.7"]
                             [lein-figwheel "0.5.18"]
                             [lein-npm "0.6.2"]
                             [lein-doo "0.1.11"]]
                   :source-paths ["dev"]}}

  :deploy-repositories [["snapshots" {:url "https://clojars.org/repo"
                                      :username :env/clojars_username
                                      :password :env/clojars_password
                                      :sign-releases false}]
                        ["releases"  {:url "https://clojars.org/repo"
                                      :username :env/clojars_username
                                      :password :env/clojars_password
                                      :sign-releases false}]]

  :release-tasks [["change" "version" "leiningen.release/bump-version" "release"]
                  ["deploy"]]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src" "dev" "test"]
                        :figwheel true
                        :compiler {:main cljs.user
                                   :output-to "target/js/compiled/dev.js"
                                   :output-dir "target/js/compiled/out"
                                   :target :nodejs
                                   :optimizations :none
                                   :source-map true}}

                       {:id "tests"
                        :source-paths ["src" "test"]
                        :compiler {:main "tests.runner"
                                   :output-to "tests-compiled/run-tests.js"
                                   :target :nodejs
                                   :optimizations :simple}}]})
