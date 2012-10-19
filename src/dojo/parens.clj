;; http://www.meetup.com/Toronto-Code-Retreat/events/85024432/

(ns dojo.parens)

(defn group-list [coll idx]
  (concat (take (dec idx) coll)
          (list (map #(nth coll %) [(dec idx) idx (inc idx)]))
          (drop (+ 2 idx) coll)))

(defn last-operation-index [coll]
  (when-let [inds (seq (filter pos? (map #(.lastIndexOf coll %) '(x))))]
    (apply max inds)))

(defn group-right-assoc [coll]
  (if-let [i (last-operation-index coll)]
    (recur (group-list coll i))
    coll))

(defn first-operation-index [coll]
  (when-let [inds (seq (filter pos? (map #(.indexOf coll %) '(+ -))))]
    (apply min inds)))

(defn group-left-assoc [coll]
  (if-let [i (first-operation-index coll)]
    (recur (group-list coll i))
    coll))

(defn group-sublists [coll]
  (-> coll
    group-right-assoc
    group-left-assoc
    first))

(defn strip-outer-parens [s]
  (subs s 1 (dec (count s))))

(defn insert-parens [s]
  (->> (.split s " ")
    (map symbol)
    group-sublists
    print-str
    strip-outer-parens))

