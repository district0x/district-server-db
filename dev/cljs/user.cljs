(ns cljs.user
  (:require
   [clojure.test :refer [run-tests]]
   [honeysql.core :as sql]
   [tests.main-test]))


(defn run-tests-sync []
  (run-tests 'tests.main-test))


(defn run-tests []
  (.nextTick js/process run-tests-sync))


(def sql-format sql/format)
