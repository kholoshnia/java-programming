import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Pokemon;

import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;
import ru.ifmo.se.pokemon.Stat;

/*********
 * Атаки
 *********/

class PoisonTail extends PhysicalMove {
    public PoisonTail() {
        super(Type.POISON , 7, 0.7);
    }

    @Override
    public java.lang.String describe() {
        return "бьет соперника хвостом";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.poison(p);
    }
}

class Swagger extends StatusMove {
    public Swagger() {
        super(Type.NORMAL , 5, 0.8);
    }

    @Override
    public java.lang.String describe() {
        return "сбивает с толку соперника, но увеличивает его атаку";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
        p.setMod(Stat.HP, 2);
    }
}

class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC , 10, 1);
    }

    @Override
    public java.lang.String describe() {
        return "засыпает и восстанавливает свое здоровье";
    }

    @Override
    public void applySelfEffects(Pokemon p) {
        Effect.sleep(p);
        p.setMod(Stat.HP, 2);
    }
}

class DarkPulse extends SpecialMove {
    public DarkPulse() {
        super(Type.DARK , 10, 0.8);
    }

    @Override
    public java.lang.String describe() {
        return "выпускает в соперника ужасную ауру, пропитанную мрачными мыслями";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.flinch(p);
    }
}

class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL , 7, 1);
    }

    @Override
    public java.lang.String describe() {
        return "атакует соперника, атака усиливается, если пользователь подожжен, отравлен или парализован";
    }

    @Override
    public void applySelfEffects(Pokemon p) {
        if (p.getCondition() == Status.BURN || p.getCondition() == Status.POISON || p.getCondition() == Status.PARALYZE)
            p.setMod(Stat.ATTACK, 2);
    }
}

class Waterfall extends PhysicalMove {
    public Waterfall() {
        super(Type.WATER , 7, 1);
    }

    @Override
    public java.lang.String describe() {
        return "атакует соперника зарядом воды";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.flinch(p);
    }
}

class DoubleTeam extends StatusMove {
    public DoubleTeam() {
        super(Type.NORMAL , 5, 1);
    }

    @Override
    public java.lang.String describe() {
        return "создает иллюзии себя";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }
}

class Liquidation extends PhysicalMove {
    public Liquidation() {
        super(Type.WATER , 10, 0.8);
    }

    @Override
    public java.lang.String describe() {
        return "врезается в соперника, используя силу струи воды";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        p.setMod(Stat.DEFENSE, -2);
    }
}

class Flamethrower extends SpecialMove {
    public Flamethrower() {
        super(Type.FIRE , 7, 0.5);
    }

    @Override
    public java.lang.String describe() {
        return "атакует соперника огнем, может поджечь";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.burn(p);
    }
}

class Sing extends StatusMove {
    public Sing() {
        super(Type.NORMAL , 5, 1);
    }

    @Override
    public java.lang.String describe() {
        return "поет успокаивающую мелодию, которая усыпляет соперника";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        Effect.sleep(p);
    }
}

class WakeUpSlap extends PhysicalMove {
    public WakeUpSlap() {
        super(Type.FIGHTING , 7, 1);
    }

    @Override
    public java.lang.String describe() {
        return "атакует соперника, если он спит, будит и наносит большой урон";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        if (p.getCondition() == Status.SLEEP)
        {
            Effect effect = new Effect();
            effect.clear();
            p.setCondition(effect);
            p.setMod(Stat.HP, -6);
        }
    }
}

class Thunder extends SpecialMove {
    public Thunder() {
        super(Type.ELECTRIC , 10, 0.8);
    }

    @Override
    public java.lang.String describe() {
        return "атакует соперника соперника сильнейшей электрической атакой, может парализовать цель";
    }

    @Override
    public void applyOppEffects(Pokemon p) {
        p.setMod(Stat.HP, -4);
        Effect.paralyze(p);
    }
}

/************
 * Покемоны
 ************/

class Seviper extends Pokemon {
    public Seviper() {
        super("Seviper", 1);
        this.setMove(new PoisonTail(), new Swagger(), new Rest(), new DarkPulse());
        this.setType(Type.POISON);
    }
}

class Wimpod extends Pokemon {
    public Wimpod() {
        super("Wimpod", 2);
        this.setMove(new Facade(), new Waterfall(), new DoubleTeam());
        this.setType(Type.BUG, Type.WATER);
    }

    public Wimpod(java.lang.String name, int level) {
        super(name, level);
        this.setMove(new Facade(), new Waterfall(), new DoubleTeam());
        this.setType(Type.BUG, Type.WATER);
    }
}

class Golisopod extends Wimpod {
    public Golisopod() {
        super("Golisopod", 3);
        this.addMove(new Liquidation());
    }
}

class Cleffa extends Pokemon {
    public Cleffa() {
        super("Cleffa", 1);
        this.setMove(new Flamethrower(), new Sing());
        this.setType(Type.FAIRY);
    }

    public Cleffa(java.lang.String name, int level) {
        super(name, level);
        this.setMove(new Flamethrower(), new Sing());
        this.setType(Type.FAIRY);
    }
}

class Clefairy extends Cleffa {
    public Clefairy() {
        super("Clefairy", 1);
        this.addMove(new WakeUpSlap());
    }

    public Clefairy(java.lang.String name, int level) {
        super(name, level);
        this.addMove(new WakeUpSlap());
    }
}

final class Clefable extends Clefairy {
    public Clefable() {
        super("Clefable", 1);
        this.addMove(new Thunder());
    }
}

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Seviper seviper = new Seviper();
        Wimpod wimpod = new Wimpod();
        Golisopod golisopod = new Golisopod();
        Cleffa cleffa = new Cleffa();
        Clefairy clefairy = new Clefairy();
        Clefable clefable = new Clefable();
        b.addAlly(seviper);
        b.addAlly(wimpod);
        b.addAlly(golisopod);
        b.addFoe(cleffa);
        b.addFoe(clefairy);
        b.addFoe(clefable);
        b.go();
    }
}