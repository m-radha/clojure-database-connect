(ns clojure-db-app.core
  (:require [clojure.java.jdbc :as jdbc]
            [clojure-db-app.db :as db]))
(defn table-exists? [conn]
  (not (empty?
        (jdbc/query conn ["SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TEST_TABLE'"]))))

  (defn create-table [conn]
    (when-not (table-exists? conn)
      (jdbc/execute! conn ["CREATE TABLE TEST_TABLE (ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), AGE INT)"])
      (println "Table created successfully!")))

;; (defn create-table [conn]
;;   (jdbc/execute! conn ["CREATE TABLE TEST_TABLE (ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), AGE INT)"]))

(defn insert-data [conn name]
  (jdbc/insert! conn "TEST_TABLE" {"NAME" name}))

(defn query-data [con]
  (jdbc/query con ["SELECT * FROM TEST_TABLE"]))

(defn operations [conn]
  (create-table conn)
  (println "Table created successfully!")
  (insert-data conn "John Doe")
  (insert-data conn "Jane Doe")
  (println "Data inserted successfully!")
  (let [data (query-data conn)]
    (println "Query executed. Result:")
    (doseq [row data]
      (println row))))

(defn -main [& args]
  (jdbc/with-db-transaction [conn db/db-spec]
    (operations conn)))








