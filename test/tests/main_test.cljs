(ns tests.main-test
  (:require
    [cljs.test :refer-macros [deftest is testing use-fixtures]]
    [district.server.db :as db :refer [db]]
    [mount.core :as mount]))

(use-fixtures
  :each
  {:before
   (fn []
     (mount/start))
   :after
   (fn []
     (mount/stop))})

(deftest test-db
  ; (println "@db === " (type @db))
  ; (is (= (str @db) "[object Database]"))

  (is (map? (db/run! {:create-table [:my-doggos]
                      :with-columns [[[:doggo/years :unsigned :integer]
                                      [:doggo/description :varchar]]]})))

  (is (= (db/run! {:insert-into :my-doggos
                   :columns [:doggo/years :doggo/description]
                   :values [[1 "Good boy"]]})
         {:changes 1 :lastInsertRowid 1}))

  (is (= (db/get {:select [:doggo/description]
                  :from [:my-doggos]
                  :where [:= :doggo/years 1]})
         {:description "Good boy"}))

  (is (= (db/all {:select [:doggo/description]
                  :from [:my-doggos]
                  :where [:= :doggo/years 1]})
         [{:description "Good boy"}]))

  (is (= (db/run! {:insert-into :my-doggos
                   :columns [:doggo/years :doggo/description]
                   :values [[2 "Bad boy"]]})
         {:changes 1 :lastInsertRowid 2}))

  (is (= (db/total-count {:select [:doggo/description]
                          :from [:my-doggos]
                          :limit 1})
         2)))
