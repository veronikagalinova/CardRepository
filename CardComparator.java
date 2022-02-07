import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

  @Override
  public int compare(Card card1, Card card2) {

    if (card1.getLastUsedOn().isBefore(card2.getLastUsedOn())) {
      return -1;
    }
    else if (card1.getLastUsedOn().isAfter(card2.getLastUsedOn())) {
      return 1;
    }
    else {
      return 0;
    }
  }
}
