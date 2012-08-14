(ns poker.core)

(defn card-ranks
  "Return a list of the ranks, sorted with higher first"
  [hand]
  (let [ranks (for [card hand] (.indexOf "--23456789TJQKA" (str (first card))))
        result (vec (reverse (sort ranks)))]
    (if (= [14 5 4 3 2] result) [5 4 3 2 1] result)))

(defn straight?
  "Return True if the ordered ranks form a 5-card straight"
  [ranks]
  (and (= 4 (- (apply max ranks) (apply min ranks)))
       (= 5 (count (set ranks)))))

(defn flush?
  "Return True if all the cards have the same suit"
  [hand]
  (let [suits (for [card hand] (second card))]
    (= 1 (count (set suits)))))

(defn kind
  "Return the first rank that this hand has exactly n of.
   Return nil if there is no n-of-a-kind in the hand"
  [n ranks]
  (let [occur (set (for [r ranks :when (= n (count (filter #(= r %) ranks)))] r))]
    (if (= 0 (count occur)) nil (first occur))))

(defn two-pair
  "If there are two pair, return the two ranks as a
   tuple: (highest, lowest); otherwise return nil"
  [ranks]
  (let [fcount (count (filter #(= (nth ranks 1) %) ranks))
        scount (count (filter #(= (nth ranks 3) %) ranks))]
    (if (and (= 2 fcount) (= 2 scount)) [(nth ranks 1) (nth ranks 3)] nil)))

(defn hand-rank
  "Return a value indicating the ranking of a hand"
  [hand]
  (let [ranks (card-ranks hand)]
    (cond
      (and (straight? ranks) (flush? hand)) [8 (apply max ranks)]
      (kind 4 ranks) [7 (kind 4 ranks) (kind 1 ranks)]
      (and (kind 3 ranks) (kind 2 ranks)) [6 (kind 3 ranks) (kind 2 ranks)]
      (flush? hand) [5 (card-ranks hand)]
      (straight? ranks) [4 (apply max ranks)]
      (kind 3 ranks) [3 (kind 3 ranks) (card-ranks hand)]
      (two-pair ranks) [2 (two-pair ranks) (card-ranks hand)]
      (kind 2 ranks) [1 (kind 2 ranks) (card-ranks hand)]
      :else [0 (card-ranks hand)])))

(defn compare-hands
  "Return comparator of two hands. This function wouldn't be needed if compare
   function compared two different-size vectors by elements, not by length.
   http://clojuredocs.org/clojure_core/clojure.core/compare"
  [h1 h2]
  (let [head-comp (compare (first h1) (first h2))]
    (if (not= 0 head-comp)
      head-comp
      (compare (vec (rest h1)) (vec (rest h2))))))

(defn poker
  "Return the best hand: (poker [hand,...]) => hand"
  [hands]
  (first (reverse (sort-by hand-rank compare-hands hands))))
