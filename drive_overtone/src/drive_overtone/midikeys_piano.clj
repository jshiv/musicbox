(use 'overtone.live)
(use 'overtone.inst.piano)
(use 'overtone.inst.synth)


(on-event [:midi :note-on]
  (fn [e]
    (let [note (:note e)
          vel  (:velocity e)
          device-name (:name (:device e))]
      (if (= "MidiKeys" device-name)
        (piano note vel)
        ()
      )
    )
  )
  ::midi-keyboard-handler
)
