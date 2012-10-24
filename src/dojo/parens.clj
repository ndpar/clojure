;; http://www.meetup.com/Toronto-Code-Retreat/events/85024432/

(ns dojo.parens
  (:use [clojure.contrib.seq :only (positions)]))

(defn index [f items coll]
  (f (positions (set items) coll)))

(defn group-triplet [coll idx]
  (let [li (- idx 1), ri (+ idx 2)]
    (concat (take li coll)
            (list (->> coll (take ri) (drop li)))
            (drop ri coll))))

(defn group-terms [pos operators coll]
  (if-let [i (index pos operators coll)]
    (recur pos operators (group-triplet coll i))
    coll))

(def group-multiplications
  (partial group-terms last '(x)))

(def group-additions
  (partial group-terms first '(+ -)))

(defn group-sublists [coll]
  (-> coll
    group-multiplications
    group-additions
    first))

(defn strip-outer-parens [s]
  (subs s 1 (dec (count s))))

(defn insert-parens [s]
  (->> (.split s " ")
    (map symbol)
    group-sublists
    print-str
    strip-outer-parens))

