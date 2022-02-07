
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CardRepositoryImpl implements CardRepository {

  private static final Integer MAX_ACTIVE_CARDS = 10;

  private Set<Card> activeCards = new HashSet<>();
  private Set<Card> blockedCards = new HashSet<>();

  @Override
  public Collection<Card> listActiveCards() {

    return Collections.unmodifiableCollection(activeCards);
  }

  @Override
  public synchronized boolean addCard(Card cardToAdd) {

    if (cardToAdd == null) {
      return false;
    }

    if (isActiveThresholdExceeded()) {
      Card lastActiveUsedCard = getLastActiveUsedCard();
      lastActiveUsedCard.setStatus(CardStatus.BLOCKED);
      activeCards.remove(lastActiveUsedCard);
      blockedCards.add(lastActiveUsedCard);
    }

    activeCards.add(cardToAdd);
    return true;
  }


  @Override
  public synchronized boolean receiveTransaction(Transaction transaction) {

    Card usedCard = transaction.getCard();
    if (!activeCards.contains(usedCard)) {
      return false;
    }

    if (transaction.getExecutedOn().isBefore(usedCard.getLastUsedOn())) {
      return true;
    }
    usedCard.setLastUsedOn(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));
    activeCards.remove(usedCard);
    activeCards.add(usedCard);

    return true;
  }


  private boolean isActiveThresholdExceeded() {

    return activeCards.size() >= MAX_ACTIVE_CARDS;
  }

  private Card getLastActiveUsedCard() {

    final CardComparator comparatorByLastUsedOn = new CardComparator();
    final TreeSet<Card> treeSet = new TreeSet<>(comparatorByLastUsedOn);
    treeSet.addAll(activeCards);
    return treeSet.first();
  }

}
