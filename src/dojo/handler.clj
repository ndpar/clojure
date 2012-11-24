(ns dojo.handler)

(defmacro handler [name args & body]
  `(fn [~'msg]
     (let [~@(mapcat (fn [x] [x `(get ~'msg ~(keyword x))]) args)]
       (when (or ~@args)
         ~@body))))

(defmacro build-handlers [& body]
  `(defn- handlers []
     [~@body]))

(build-handlers
  (handler doc [a c]
    (format "Document:%s:%s" a c))

  (handler note [b c]
    (format "Note:%s:%s" b c))

  (handler alert [a b]
    (format "Alert:%s:%s" a b)))

(defn on-message [msg]
  (letfn [(handle [acc h]
            (if-let [res (h msg)]
              (conj acc res)
              acc))]
    (reduce handle [] (handlers))))
