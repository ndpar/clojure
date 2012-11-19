(ns dojo.handler)

(defn- doc-handler [msg]
  (let [a (msg :a), c (msg :c)]
    (when (or a c)
      (format "Document:%s:%s" a c))))

(defn- note-handler [msg]
  (let [b (msg :b), c (msg :c)]
    (when (or b c)
      (format "Note:%s:%s" b c))))

(defn- alert-handler [msg]
  (let [a (msg :a), b (msg :b)]
    (when (or a b)
      (format "Alert:%s:%s" a b))))

(defn- handlers []
  [doc-handler note-handler alert-handler])

(defn on-message [msg]
  (letfn [(handle [acc h]
            (if-let [res (h msg)]
              (conj acc res)
              acc))]
    (reduce handle [] (handlers))))
