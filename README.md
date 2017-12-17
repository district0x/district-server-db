# district-server-db

Clojurescript-node.js [mount](https://github.com/tolitius/mount) component for a district server, that takes care of database, which usually stores blockchain data in more search-friendly format. This component currently utilises [better-sqlite3](https://github.com/JoshuaWise/better-sqlite3) for db and [honeysql](https://github.com/jkk/honeysql) for SQL formatting.

## Installation
Add `[district0x/district-server-db "1.0.0"]` into your project.clj  
Include `[district.server.db]` in your CLJS file, where you use `mount/start`

**Warning:** district0x components are still in early stages, therefore API can change in a future.

## Usage
You can pass following args to smart-contracts module: 
* `:path` Path to db file in case you don't use in-memory db
* `:sqlite3-opts` Opts passed into Sqlite3 constructor. Default: `{:memory true}`


```clojure
  (ns my-district
    (:require [mount.core :as mount]
              [district.server.db :as database :refer [db]]))

  (-> (mount/with-args
        ;; This is default, so not really needed, but here just to show how to pass args to the component
        {:db {:sqlite3-opts {:memory true}}})
    (mount/start))

  (println @db)
  ;; #object[Database [object Database]] (Sqlite3 Database Connection)

  (database/run! {:create-table :my-doggos
                  :with-columns [[[:doggo/years :unsigned :integer]
                                  [:doggo/description :varchar]]]})
  ;; => "{:changes 0, :lastInsertROWID 0}"

  (database/run! {:insert-into :my-doggos
                  :columns [:doggo/years :doggo/description]
                  :values [[1 "Good boy"]]})
  ;; => "{:changes 1, :lastInsertROWID 1}"

  (database/get {:select [:doggo/description]
                 :from [:my-doggos]
                 :where [:= :doggo/years 1]})
  ;; => {:doggo/description "Good boy"}
```
Note: You can use namespaced keywords for SQL column and table names and also get them back from selects. This is because `district.server.db` extends honeysql name transformation with [munge](https://cljs.github.io/api/cljs.core/munge), [demunge](https://cljs.github.io/api/cljs.core/demunge) functions.

`district.server.db` also includes [honeysql-postgres](https://github.com/nilenso/honeysql-postgres) extensions, so that's why we could use statements such as `:create-table` in example, which are not supported by honeysql by default.

## Component dependencies

### [district-server-config](https://github.com/district0x/district-server-config)
`district-server-db` can get initial args from config provided by `district-server-config/config` under the key `:db`. These args are then merged together by ones passed to `mount/with-args`.

If you wish to use custom components instead of dependencies above while still using `district-server-db`, you can easily do so by [mount's states swapping](https://github.com/tolitius/mount#swapping-states-with-states).

## district.server.db
This namespace contains following functions for working with database:
#### `run! [sql-map]`
Runs sqlite3 [run](https://github.com/JoshuaWise/better-sqlite3/wiki/API#runbindparameters---object).

#### `get [sql-map]`
Gets single result. Runs sqlite3 [get](https://github.com/JoshuaWise/better-sqlite3/wiki/API#getbindparameters---row).

#### `all [sql-map]`
Gets list of results. Runs sqlite3 [all](https://github.com/JoshuaWise/better-sqlite3/wiki/API#allbindparameters---array-of-rows).

#### `total-count-query [sql-map & [opts]]`
Takes honeysql sql-map and converts it to one that has same conditions, but no offset, limit, ordering and selects count. Useful when you need to get total count of result, e.g for pagination. 

#### `total-count [sql-map & [opts]]`
Converts sql-map with `total-count-query` and then runs query. Returns only single total count number. 

#### `order-by-similarity [column-name string opts]`
Helper function to create honeysql statement for `:order-by`, to order by most similar string. This is not full-text search, only done by SQL's `LIKE`. 

## district.server.db.honeysql-extensions
This namespace defines additional honeysql extensions for working with sqlite3:
##### CREATE INDEX
`{:create-index :some-column-index :on [:my-table :some-column]}`

## district.server.db.column-types
Contains most common types for convenience, when creating SQL tables

