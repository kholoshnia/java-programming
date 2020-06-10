import java.util.Objects;

interface Organism {
    enum Organisms { RUDIMENTARYFISH, MOLLUSK, CORAL };
    default String getPeriod() {
        return "Comanche";
    }
    default String getInformation() {
        return getFullName() + " - refers to the large source of carbon-based "
                + "compounds found within natural and engineered, terrestrial "
                + "and aquatic environments. It is matter composed of organic "
                + "compounds that have come from the remains of organisms such "
                + "as plants and animals and their waste products in the environment.";
    }
    default String getFullName() {
        return "Organic remains";
    }
}

class RudimentaryFish implements Organism {
    final Organisms type;

    RudimentaryFish() {
        type = Organisms.RUDIMENTARYFISH;
    }

    @Override
    public String getFullName() {
        return "Rudimentary fish";
    }

    @Override
    public String getInformation() {
        return getFullName() + " - rudimentary fish ancestors "
                + "evolved into the first four-legged "
                + "animals, tetrapods, 380 million years "
                + "ago. They are the forerunners of all "
                + "birds, mammals, crustaceans, and batrachians. "
                + "Since limbs and their fingers are so important "
                + "to evolution, researchers have long wondered "
                + "whether they appeared for the first time "
                + "in tetrapods, or whether they had evolved from "
                + "elements that already existed in their fish ancestors.";
    }

    @Override
    public int hashCode() {
        return 53 * 7 + Objects.hashCode(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RudimentaryFish other = (RudimentaryFish) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rudimentary fish";
    }
}

class Mollusk implements Organism {
    final Organisms type;

    Mollusk() {
        type = Organisms.MOLLUSK;
    }

    @Override
    public String getFullName() {
        return "Mollusk";
    }

    @Override
    public String getInformation() {
        return getFullName() + " - an invertebrate of a "
                + "large phylum which includes snails, "
                + "slugs, mussels, and octopuses. They "
                + "have a soft unsegmented body and live "
                + "in aquatic or damp habitats, and most "
                + "kinds have an external calcareous shell.";
    }

    @Override
    public int hashCode() {
        return 37 * 7 + Objects.hashCode(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Mollusk other = (Mollusk) obj;
        return this.type == other.type;
    }

    @Override
    public String toString() {
        return "Mollusk";
    }
}

class Coral implements Organism {
    final Organisms type;

    Coral() {
        type = Organisms.CORAL;
    }

    @Override
    public String getFullName() {
        return "Coral";
    }

    @Override
    public String getInformation() {
        return getFullName() + " - a hard stony substance secreted "
                + "by certain marine coelenterates as an external "
                + "skeleton, typically forming large reefs in warm seas.";
    }

    @Override
    public int hashCode() {
        return 53 * 5 + Objects.hashCode(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coral other = (Coral) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coral";
    }
}

interface Letter extends Organism {
    public enum Letters { SUMMARY, DENUNCIATION };
    default String getLetterAbout(Organism organism) {
        return "Period: " + getPeriod() + " Letter about " + organism.getFullName() + organism.getInformation();
    }
    public String sendLetter(Letters type, Organism organism);
    public String dictateLetter(Letters type, Organism organism);
}

abstract class Explorer implements Letter {
    private final String name;

    Explorer(String name) { this.name = name; }
    public String getName() { return name; }
};

class Lake extends Explorer {
    Lake() {
        super("Lake");
    }

    @Override
    public String sendLetter(Letters type, Organism organism) {
        switch(type) {
            case SUMMARY:
                return getName() + " - sends summary: " + getLetterAbout(organism);
            case DENUNCIATION:
                return getName() + " - sends denunciation: " + getLetterAbout(organism);
            default:
                return "Wrong letter type";
        }
    }

    @Override
    public String dictateLetter(Letters type, Organism organism) {
        return "In this text, Lake does not dictate a letter";
    }

    @Override
    public int hashCode() {
        return 53 * 3 + Objects.hashCode(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lake other = (Lake) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Explorer: " + this.getName();
    }
};

class Multon extends Explorer {
    Multon() {
        super("Multon");
    }

    @Override
    public String sendLetter(Letters type, Organism organism) {
        return "In this text, Multon does not send a letter";
    }

    @Override
    public String dictateLetter(Letters type, Organism organism) {
        switch(type) {
            case SUMMARY:
                return getName() + " - dictates summary: " + getLetterAbout(organism);
            case DENUNCIATION:
                return getName() + " - dictates denunciation: " + getLetterAbout(organism);
            default:
                return "Wrong letter type";
        }
    }

    @Override
    public int hashCode() {
        return 23 * 7 + Objects.hashCode(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Multon other = (Multon) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Explorer: " + this.getName();
    }
};

public class Main {
    public static void main(String[] args) {
        RudimentaryFish rudimentaryFish = new RudimentaryFish();
        Mollusk mollusk = new Mollusk();
        Coral coral = new Coral();

        Lake lake = new Lake();
        System.out.println(lake.sendLetter(Letter.Letters.SUMMARY, rudimentaryFish));
        System.out.println(lake.sendLetter(Letter.Letters.DENUNCIATION, mollusk));

        Multon multon = new Multon();
        System.out.println(multon.dictateLetter(Letter.Letters.SUMMARY, rudimentaryFish));
        System.out.println(multon.dictateLetter(Letter.Letters.DENUNCIATION, coral));
    }
}