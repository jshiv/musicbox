(use 'overtone.live)
(use 'overtone.inst.piano)
(use 'overtone.inst.synth)
(use 'overtone.inst.sampled-piano)


(sampled-piano )
(def liveatom (atom {:note 0 :bend 0}))

(on-event [:midi :note-on]
          (fn [e]
            (let [note (:note e)
                  vel (:velocity-f e)]
              (swap! liveatom assoc :note note)
              (sampled-piano (@liveatom :note) vel )))
          :event-printer)




(on-event [:midi :note-off]
          (fn [e]
            (reset! note_atom))
          :note-stopper)

(on-event [:midi :pitch-bend]
          (fn [e]
            (let [bend (:velocity e)]
              (swap! liveatom assoc :bend bend)
              (sampled-piano (+ (@liveatom :note) (@liveatom :bend)))))
          :event-bender)





(defonce memory (agent {}))



(on-event [:midi :note-on]
          (fn [m]
            (send memory
                  (fn [mem]
                    (let [n (:note m)
                          s (sampled-piano :note n)]
                      (assoc mem n s)))))
          ::play-note)

(on-event [:midi :pitch-bend]
          (fn [m]
            (send memory
                  (fn [mem]
                    (let [p (:velocity m)
                          (when-let [s (get mem n)]
                              (ctl s :note p))]))))
          :bend-pitch)

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
