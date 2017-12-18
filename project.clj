(defproject district0x/district-server-db "1.0.0"
  :description "district0x server component for setting up database connection"
  :url "https://github.com/district0x/district-server-db"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[district0x/district-server-config "1.0.0"]
                 ;[honeysql "0.9.1"]
                 ;; Until PR https://github.com/jkk/honeysql/pull/195 is merged
                 [madvas/honeysql "0.9.1"]
                 [mount "0.1.11"]
                 ;[nilenso/honeysql-postgres "0.2.3" :exclusions [honeysql]]
                 ;; Until PR https://github.com/nilenso/honeysql-postgres/pull/13 is merged
                 [madvas/honeysql-postgres "0.2.3" :exclusions [honeysql]]
                 [org.clojure/clojurescript "1.9.946"]]

  :npm {:dependencies [[better-sqlite3 "4.0.3"]]
        :devDependencies [[ws "2.0.1"]]}

  :figwheel {:server-port 4678}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [figwheel-sidecar "0.5.14"]
                                  [org.clojure/tools.nrepl "0.2.13"]]
                   :plugins [[lein-cljsbuild "1.1.7"]
                             [lein-figwheel "0.5.14"]
                             [lein-npm "0.6.2"]
                             [lein-doo "0.1.7"]]
                   :source-paths ["dev"]}}

  :cljsbuild {:builds [{:id "tests"
                        :source-paths ["src" "test"]
                        :figwheel {:on-jsload "tests.runner/-main"}
                        :compiler {:main "tests.runner"
                                   :output-to "tests-compiled/run-tests.js"
                                   :output-dir "tests-compiled"
                                   :target :nodejs
                                   :optimizations :none
                                   :source-map true}}]})
