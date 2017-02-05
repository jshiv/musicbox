(ns drive-overtone.sketch)
(use 'overtone.live)
(use 'drive-overtone.sounds)
(use 'overtone.live sampled-piano)

(sampled-piano)
  (defonce memory (agent {}))



  (on-event [:midi :note-on]
            (fn [m]
              (send memory
                    (fn [mem]
                      (let [n (:note m)
                            s (pad2 :freq (midi->hz n))]
                        (assoc mem n s)))))
            ::play-note)

  (on-event [:midi :pitch-bend]
            (fn [m]
              (send memory
                    (fn [mem]
                    (let [p (:velocity m)
                              (when-let [s (get mem n)]
                                (ctl s :freq p))]))))
              ::bend-pitch)

  (on-event [:midi :note-off]
            (fn [m]
              (send memory
                    (fn [mem]
                      (let [n (:note m)]
                        (when-let [s (get mem n)]
                          (ctl s :gate 0))
                        (dissoc mem n))))
  )
            ::release-note)
