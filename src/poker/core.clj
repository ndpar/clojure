(ns poker.core)

(defn card-ranks
  "Return a list of the ranks, sorted with higher first"
  [hand]
  (let [ranks (map #(.indexOf "--23456789TJQKA" (str (first %))) hand)
        result (vec (sort > ranks))]
    (if (= [14 5 4 3 2] result) [5 4 3 2 1] result)))

(defn straight?
  "Return true if the ordered ranks form a 5-card straight"
  [ranks]
  (and (= 4 (- (first ranks) (last ranks)))
       (= 5 (count (set ranks)))))

(defn flush?
  "Return true if all the cards have the same suit"
  [hand]
  (= 1 (count (set (map second hand)))))

(defn kind
  "Return the first rank that this hand has exactly n of.
   Return nil if there is no n-of-a-kind in the hand"
  [n ranks]
  (->> (frequencies ranks) (filter #(= (second %) n)) (map first) (sort) (last)))

(defn two-pair
  "If there are two pair, return the two ranks as a
   tuple: (highest, lowest); otherwise return nil"
  [ranks]
  (let [how-many (fn [x col] (count (filter #(= x %) col)))
        {high 1, low 3} ranks
        hcount (how-many high ranks)
        lcount (how-many low ranks)]
    (if (= 2 hcount lcount) [high low] nil)))

(defn hand-rank
  "Return a value indicating the ranking of a hand"
  [hand]
  (let [ranks (card-ranks hand)]
    (cond
      (and (straight? ranks) (flush? hand)) [8 (first ranks)]
      (kind 4 ranks) [7 (kind 4 ranks) (kind 1 ranks)]
      (and (kind 3 ranks) (kind 2 ranks)) [6 (kind 3 ranks) (kind 2 ranks)]
      (flush? hand) [5 ranks]
      (straight? ranks) [4 (first ranks)]
      (kind 3 ranks) [3 (kind 3 ranks) ranks]
      (two-pair ranks) [2 (two-pair ranks) ranks]
      (kind 2 ranks) [1 (kind 2 ranks) ranks]
      :else [0 ranks])))

(defn compare-seq
  "Return comparator of two hands. This function wouldn't be needed if compare
   function compared two different-size vectors by elements, not by length.
   http://clojuredocs.org/clojure_core/clojure.core/compare"
  [h1 h2]
  (let [head-comp (compare (first h1) (first h2))]
    (if (and (zero? head-comp) h1 h2)
      (recur (next h1) (next h2))
      head-comp)))

(defn poker
  "Return the best hand: (poker [hand,...]) => hand"
  [hands]
  (last (sort-by hand-rank compare-seq hands)))
