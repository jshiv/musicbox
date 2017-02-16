# Welcome to Sonic Pi v2.11.1

notes = (ring :d, :r, :r, :a, :f5, :r, :a, :r)
##| notes = (ring chord(:D3, :m), :r, :r, :a, :f5, :r, :a, :r)
with_fx :reverb, room: 0.8 do
  live_loop :habanera do
    with_fx :ring_mod, freq: 1, mix: 0.4 do
      use_synth :dark_ambience
      use_transpose -12
      play notes.tick, release: 1.5, decay: 1.5, amp: 0.5
      sleep 0.25
      
    end
  end
end



with_fx :flanger do
  with_fx :krush do
    live_loop :break do
      if one_in(2)
        sample :drum_heavy_kick, amp: 1
        sleep 0.5/4
      else
        sample :sn_dolf, amp: 1
        sleep 0.5/4
      end
      
      if one_in(4)
        sample :drum_heavy_kick, amp: 2
        
      else
        sample :drum_cymbal_closed, amp: 1
        sleep 0.125
        
      end
    end
  end
end