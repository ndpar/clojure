;; http://en.wikipedia.org/wiki/Dining_philosophers_problem

(ns dojo.philosophers)

(defn forks [n] (mapv ref (repeat n 0)))

(defn enjoy-meal [] (Thread/sleep (rand-int 500)))

(defn eat [phil forks]
  (let [place (map #(mod % (count forks)) [phil (inc phil)])]
    (dosync
      (alter (forks (first place)) inc) ; grab right fork
      (alter (forks (second place)) inc) ; grab left fork
      (enjoy-meal))))

(defn log [thread & message]
  (println (System/currentTimeMillis) ":" (inc thread) message))

(defn think [] (Thread/sleep (rand-int 500)))

(defn philosopher [id meals forks]
  (dotimes [m meals]
    (log id "thinking")
    (think)
    (log id "hungry")
    (eat id forks)
    (log id "ate" (inc m) "meal")))

(defn dine
  ([people meals]
   (dine people meals (forks people)))
  ([people meals forks]
   (let [threads (map (fn [id] #(philosopher id meals forks)) (range people))]
     (dorun (apply pcalls threads)))))

