(ns myblog.model
  (:use [korma db core]))



(def default-conn {:classname "com.mysql.jdbc.Driver"
                   :subprotocol "mysql"
                   :user "myblog"
                   :password "myblog"
                   :subname "//127.0.0.1:3306/myblog?useUnicode=true&characterEncoding=utf8"
                   :delimiters "`"})

;; (def env (into {} (System/getenv)))

;; (def dbhost (get env "OPENSHIFT_MYSQL_DB_HOST"))
;; (def dbport (get env "OPENSHIFT_MYSQL_DB_PORT"))

;; (def default-conn {:classname "com.mysql.jdbc.Driver"
;;                    :subprotocol "mysql"
;;                    :user "user"
;;                    :password "password"
;;                    :subname (str "//" dbhost ":" dbport "/helloworld?useUnicode=true&characterEncoding=utf8")
;;                    :delimiters "`"})


(defdb korma-db default-conn)

(defentity article)


(defn create-article [item]
  (insert article (values item)))

(defn select-article []
  (select article))

(defn find-article [id]
  (first (select article (where {:id id}))))

(defn update-article [item]
  (update article
          (set-fields item)
          (where {:id (:id item)})))

(defn delete-article [id]
  (delete article
          (where {:id id})))


