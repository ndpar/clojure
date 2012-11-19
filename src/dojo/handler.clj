(ns dojo.handler)

(defmacro handler [name args & body]
  `(defn- ~(symbol (str name "-handler")) [~'msg]
     (let [~@(interleave args (map (fn [x] `(get ~'msg ~(keyword x))) args))]
       (when (or ~@args)
         ~@body))))

(handler doc [a c]
  (format "Document:%s:%s" a c))

(handler note [b c]
  (format "Note:%s:%s" b c))

(handler alert [a b]
  (format "Alert:%s:%s" a b))

(defn- handlers []
  [doc-handler note-handler alert-handler])

(defn on-message [msg]
  (letfn [(handle [acc h]
            (if-let [res (h msg)]
              (conj acc res)
              acc))]
    (reduce handle [] (handlers))))
