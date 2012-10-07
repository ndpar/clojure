;; http://en.wikipedia.org/wiki/A*_search_algorithm

(ns joy.astar
  "http://github.com/joyofclojure/book-source/blob/master/src/joy/a.clj")

(defn neighbors
  ([size yx]
   (neighbors [[-1 0] [1 0] [0 -1] [0 1]] size yx))
  ([deltas size yx]
   (filter (fn [new-yx] (every? #(< -1 % size) new-yx))
           (map #(mapv + yx %) deltas))))

(defn estimate-cost [step-cost-est sz y x]
  (* step-cost-est 
     (- (+ sz sz) y x 2)))

(defn path-cost [node-cost cheapest-nbr]
  (+ node-cost
     (or (:cost cheapest-nbr) 0)))

(defn total-cost [newcost step-cost-est size y x]
  (+ newcost 
     (estimate-cost step-cost-est size y x)))

(defn min-by [f coll]
  (when (seq coll)
    (reduce (fn [min this]
              (if (> (f min) (f this)) this min))
            coll)))

(defn astar [start-yx step-est cell-costs]
  (let [size (count cell-costs)]
;    (println "start-yx:" start-yx "step-est:" step-est "cell-costs:" cell-costs)
    (loop [steps 0
           routes (vec (repeat size (vec (repeat size nil))))
           work-todo (sorted-set [0 start-yx])]
;      (println "----------------------------")
;      (println "routes:" routes)
;      (println "work-todo:" work-todo)
      (if (empty? work-todo) ; Check done
        [(peek (peek routes)) :steps steps] ; Grab the first route
        (let [[_ yx :as work-item] (first work-todo) ; Get next work item
              rest-work-todo (disj work-todo work-item) ; Clear from todo
              nbr-yxs (neighbors size yx) ; Get neighbors
              cheapest-nbr (min-by :cost ; Calculate least-cost
                                   (keep #(get-in routes %) 
                                         nbr-yxs))
              newcost (path-cost (get-in cell-costs yx) ; Calculate path so-far
                                 cheapest-nbr)
              oldcost (:cost (get-in routes yx))]
;          (println "yx:" yx "work-item:" work-item "rest-work-todo:" rest-work-todo)
;          (println "nbr-yxs:" nbr-yxs "cheapest-nbr:" cheapest-nbr)
;          (println "newcost:" newcost "oldcost:" oldcost)
          (if (and oldcost (>= newcost oldcost)) ; Check if new is worse
            (recur (inc steps) routes rest-work-todo)
            (recur (inc steps) ; Place new path in the routes
                   (assoc-in routes yx
                             {:cost newcost 
                              :yxs (conj (:yxs cheapest-nbr []) 
                                         yx)})
                   (into rest-work-todo ; Add the estimated path to the todo and recur
                         (map 
                           (fn [w] 
                             (let [[y x] w]
                               [(total-cost newcost step-est size y x) w]))
                           nbr-yxs)))))))))

