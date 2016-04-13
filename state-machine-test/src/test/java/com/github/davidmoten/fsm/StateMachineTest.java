package com.github.davidmoten.fsm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.davidmoten.fsm.example.In;
import com.github.davidmoten.fsm.example.Out;
import com.github.davidmoten.fsm.example.Ship;
import com.github.davidmoten.fsm.example.generated.ShipBehaviour;
import com.github.davidmoten.fsm.example.generated.ShipBehaviourBase;
import com.github.davidmoten.fsm.example.generated.ShipStateMachine;
import com.github.davidmoten.fsm.runtime.Create;

public class StateMachineTest {

    @Test
    public void testRuntime() {
        final Ship ship = new Ship("12345", "6789", 35.0f, 141.3f);
        List<Integer> list = new ArrayList<>();
        ShipBehaviour shipBehaviour = new ShipBehaviourBase() {

            @Override
            public Ship onEntry_Outside(Ship ship, Out out) {
                list.add(1);
                return new Ship(ship.imo(), ship.mmsi(), out.lat, out.lon);
            }

            @Override
            public Ship onEntry_NeverOutside(Create created) {
                list.add(2);
                return ship;
            }

            @Override
            public Ship onEntry_InsideNotRisky(Ship ship, In in) {
                list.add(3);
                return new Ship(ship.imo(), ship.mmsi(), in.lat, in.lon);
            }

        };
        ShipStateMachine.create(shipBehaviour)
                //
                .event(Create.instance())
                //
                .event(new In(1.0f, 2.0f))
                //
                .event(new Out(1.0f, 3.0f));

        assertEquals(Arrays.asList(2, 1), list);
    }

}
