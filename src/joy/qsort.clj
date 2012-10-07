;; Lazy, tail-recursive, incremental quicksort.
;;
;; Extremely efficient n-order statistics algorithm
;; (http://en.wikipedia.org/wiki/Order_statistic).
;; Gives you O(n) performance. 

(ns joy.qsort
  "https://github.com/joyofclojure/book-source/blob/master/src/joy/q.clj")

(defn sort-parts [work]
;  (println "----->" work)
  (lazy-seq
    (loop [[part & parts] work]
;      (println "part:" part "parts:" parts)
      (if-let [[pivot & xs] (seq part)]
        (let [smaller? #(< % pivot)]
;          (println "pivot: " pivot " xs: " xs)
          (recur (list*
                   (filter smaller? xs)
                   pivot
                   (remove smaller? xs)
                   parts)))
        (when-let [[x & parts] parts]
;          (println "x->" x "parts->" parts)
          (cons x (sort-parts parts)))))))

(defn qsort [xs]
  (sort-parts (list xs)))

