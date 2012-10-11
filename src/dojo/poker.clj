(ns dojo.poker)

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
       (= 5 (count (distinct ranks)))))

(defn flush?
  "Return true if all the cards have the same suit"
  [hand]
  (= 1 (count (distinct (map second hand)))))

(defn n-kinds [n ranks]
  (filter #(= (count %) n) (partition-by identity ranks)))

(defn kind
  "Return the first rank that this hand has exactly n of.
   Return nil if there is no n-of-a-kind in the hand"
  [n ranks]
  (-> (n-kinds n ranks) first first))

(defn two-pair
  "If there are two pairs, return the two ranks as a
   tuple: (highest, lowest); otherwise return nil"
  [ranks]
  (let [pairs (n-kinds 2 ranks)]
    (cond (= 2 (count pairs)) (first (apply map vector pairs)))))

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

(def restv (comp vec rest))

(defn poker
  "Return the best hand: (poker [hand,...]) => hand"
  [hands]
  (last (sort-by (comp (juxt first restv) hand-rank) hands)))
