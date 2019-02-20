(ns tests.runner
  (:require
    [cljs.nodejs :as nodejs]
    [cljs.test :refer [run-tests]]
    [tests.main-test]))

(nodejs/enable-util-print!)

(defn -main [& _]
  (run-tests 'tests.main-test))

(set! *main-cli-fn* -main)
