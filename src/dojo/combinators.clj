;; http://mvanier.livejournal.com/2897.html

(ns dojo.combinators)

(defn Y [f]
  ((fn [x] (x x))
    (fn [x] (f (fn [y] ((x x) y))))))

(defn almost-factorial [f]
  #(if (= % 0)
    1
    (* % (f (- % 1)))))

(defn almost-triangular [f]
  (fn [n]
    (if (= n 0)
        1
        (+ n (f (- n 1))))))

(def factorial (Y almost-factorial))
(def triangular (Y almost-triangular))
