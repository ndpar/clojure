;; Clojure Programming, p. 181
;; The Mechanics of Ref Change

(ns cljprog.hobbit)

;; Utilities
;; https://github.com/clojurebook/ClojureProgramming/blob/master/ch04-concurrency-game/src/com/clojurebook/concurrency.clj

(defmacro futures [n & exprs]
  (vec (for [_ (range n)
             expr exprs]
         `(future ~expr))))

(defmacro wait-futures [& args]
  `(doseq [f# (futures ~@args)]
     @f#))

;; Game functions
;; https://github.com/clojurebook/ClojureProgramming/blob/master/ch04-concurrency-game/src/com/clojurebook/concurrency/game.clj

(defn character [name & {:as opts}]
  (ref (merge {:name name :health 500 :items #{}}
              opts)))

(defn loot
  "Transfers one value from (:items @from) to (:items @to).
   Assumes that each is a set. Returns the new state of from."
  [from to]
  (dosync
    (when-let [item (first (:items @from))]
      #_(Thread/sleep (rand-int 10))
      (commute to update-in [:items] conj item)
      (alter from update-in [:items] disj item))))

(defn attack [aggressor target]
  (dosync
    (let [damage (* (rand 0.1) (:strength @aggressor))]
      (commute target update-in [:health] #(max 0 (- % damage))))))

(defn heal [healer target]
  (dosync
    (let [aid (* (rand 0.1) (:mana @healer))]
      (when (pos? aid)
        (commute healer update-in [:mana] - (max 5 (/ aid 5)))
        (commute target update-in [:health] + aid)))))

(def alive? (comp pos? :health))

(defn play [character action other]
  (while (and (alive? @character)
              (alive? @other)
              (action character other))
    (Thread/sleep (rand-int 10))))
