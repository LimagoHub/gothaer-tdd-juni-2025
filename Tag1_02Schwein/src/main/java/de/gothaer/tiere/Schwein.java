package de.gothaer.tiere;

import java.util.Objects;

public class Schwein {


    public static final int DEFAULT_WEIGHT = 10;

    private String name;
    private int gewicht;

    public Schwein() {
        this("Nobody");
    }

    public Schwein(final String name) {
        setName(name);
        setGewicht(DEFAULT_WEIGHT);
    }

    public String getName() {
        return name;
    }

    public final void setName(final String name) {
        if (name == null || "elsa".equalsIgnoreCase(name)) throw new IllegalArgumentException("Unerlaubter Name");
        this.name = name;
    }

    public int getGewicht() {
        return gewicht;
    }

    private void setGewicht(final int gewicht) {
        this.gewicht = gewicht;
    }

    public void futtern() {
        setGewicht(getGewicht() + 1);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Schwein schwein = (Schwein) o;
        return gewicht == schwein.gewicht && Objects.equals(name, schwein.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gewicht);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Schwein{");
        sb.append("name='").append(name).append('\'');
        sb.append(", gewicht=").append(gewicht);
        sb.append('}');
        return sb.toString();
    }
}
