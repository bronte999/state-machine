package com.github.davidmoten.fsm.runtime;

import java.util.List;
import java.util.Optional;

public interface EntityStateMachine<T, Id> extends ObjectState<T> {

    boolean transitionOccurred();

    Optional<? extends EntityState<T>> previousState();

    EntityStateMachine<T, Id> signal(Event<? super T> event);

    List<Event<? super T>> signalsToSelf();

    List<Signal<?, ?>> signalsToOther();

    Class<T> cls();

    EntityStateMachine<T, Id> withSearch(Search<Id> search);

    EntityStateMachine<T, Id> withClock(Clock clock);

    Clock clock();

    Optional<Event<? super T>> event();

    Id id();

    EntityStateMachine<T, Id> replaying();

    EntityStateMachine<T, Id> withPreTransition(
            Action3<? super EntityStateMachine<T, Id>, ? super Event<? super T>, ? super EntityState<T>> action);

}
