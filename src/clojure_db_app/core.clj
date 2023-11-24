(ns clojure-db-app.core

  (:require [clojure.java.jdbc :as jdbc]

            [clojure-db-app.db :as db]))

(defn create-table []
  (jdbc/with-db-connection [conn db/db-spec]
    (jdbc/execute! conn
                   ["CREATE TABLE test_table (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT)"])))


;; (defn insert-data [name]
;;   (jdbc/with-db-connection [db-conn db/db-spec]
;;     (jdbc/insert! db-conn
;;                   :test_table
;;                   {:name name})))

;; (defn query-data []

;;   (jdbc/with-db-connection [db-conn db/db-spec]

;;     (jdbc/query db-conn ["SELECT * FROM test_table"])))

(defn insert-data [name]
  (jdbc/with-db-connection [db-conn db/db-spec]
    (jdbc/insert! db-conn
                  :test_table ;; Ensure consistent casing here
                  {:name name})))

(defn query-data []
  (jdbc/with-db-connection [db-conn db/db-spec]
    (jdbc/query db-conn ["SELECT * FROM test_table"])))


(defn -main [& args]
  ;; Call functions or perform operations here when the program is executed
   (create-table)
   (println "Table created successfully!")

  ;; Call insert-data function
   (insert-data "John Doe")
   (insert-data "Jane Doe")
   (println "Data inserted successfully!")

  ;; Call query-data function
   (let [data (query-data)]
     (println "Query executed. Result:")
     (doseq [row data]
       (println row))))







