(ns myblog.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [myblog.model :as model]
            [myblog.view :as view]
            [ring.util.response :as resp]
            [noir.util.middleware :as noir]
            [noir.session :as session]
            ))


(defn pint [s-int]
  (Integer/parseInt s-int))


(defn show-article-list []
  (view/show-article-list (model/select-article)))

(defn show-article [id]
  (view/show-article (model/find-article (pint id))))



(defn edit-article [id]
  (view/edit-article (model/find-article (pint id))))


(defn delete-article [id]
  (model/delete-article (pint id))
  (resp/redirect "/articles"))


(defn update-article [id header content]
  (let [article {:id (pint id), :header header, :content content}]
    (model/update-article article)
    (view/show-article article)))


(defn show-new-article []
  (view/show-new-article))

(defn create-article [article]
  (model/create-article article)
  (resp/redirect "/articles"))


(defroutes app-routes
  
  (GET "/" [] (resp/redirect "/articles"))
  
  ;; Show articles list
  (GET "/articles" [] (show-article-list))

  ;; Show form for a new article
  (GET "/article/new" [] (show-new-article))

  ;; Create new article
  (POST "/article/create" req (create-article (:params req)))

  ;; Show article details
  (GET "/article/:id" [id] (show-article id))

  ;; Show form for editting article
  (GET "/article/edit/:id" [id] (edit-article id))

  ;; Update article
  (POST "/article/update/:id" [id header content] (update-article id header content))

  ;; Delete article
  (POST "/article/delete/:id" [id] (delete-article id))

  (route/resources "/") 
  (route/not-found "Not Found"))

(def app
  (->
   [(handler/site app-routes)]
   noir/app-handler
   noir/war-handler
   ))



(comment
  ;; Function for inspecting java objects
  (use 'clojure.pprint)
  (defn show-methods [obj]
    (-> obj .getClass .getMethods vec pprint))
  
  )


