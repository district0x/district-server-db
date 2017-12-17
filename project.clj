(defproject district0x/district-server-db "1.0.0"
  :description "district0x server component for setting up database connection"
  :url "https://github.com/district0x/district-server-db"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[cljs-web3 "0.19.0-0-8"]
                 [district0x/district-server-config "1.0.0"]
                 ;[honeysql "0.9.1"]
                 ;; Until PR https://github.com/jkk/honeysql/pull/195 is merged
                 [madvas/honeysql "0.9.1"]
                 [mount "0.1.11"]
                 ;[nilenso/honeysql-postgres "0.2.3" :exclusions [honeysql]]
                 ;; Until PR https://github.com/nilenso/honeysql-postgres/pull/13 is merged
                 [madvas/honeysql-postgres "0.2.3" :exclusions [honeysql]]
                 [org.clojure/clojurescript "1.9.946"]]

  :npm {:dependencies [[better-sqlite3 "4.0.3"]]})
