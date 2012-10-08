;; http://en.wikipedia.org/wiki/Dining_philosophers_problem

(ns dojo.philosophers)

(defn now [] (System/currentTimeMillis))

(def logger (agent []))

(defn do-log [log message]
  (conj log message))

(defn log [thread action time]
  (send-off logger do-log [time (inc thread) action]))


(defn forks [n] (mapv ref (repeat n 0)))

(defn eat [phil forks]
  (let [place (map #(mod % (count forks)) [phil (inc phil)])]
    (dosync
      (log phil [:eating :begin] (now))
      (alter (forks (first place)) inc) ; grab right fork
      (alter (forks (second place)) inc) ; grab left fork
      (Thread/sleep (rand-int 200))
      (log phil [:eating :end] (now)))))

(defn think [phil]
  (log phil [:thinking :begin] (now))
  (Thread/sleep (rand-int 200))
  (log phil [:thinking :end] (now)))

(defn philosopher [id meals forks]
  (dotimes [m meals]
    (think id)
    (eat id forks)))

(defn dine
  ([people meals]
   (dine people meals (forks people)))
  ([people meals forks]
   (let [threads (map (fn [id] #(philosopher id meals forks)) (range people))]
     (dorun (apply pcalls threads)))))

