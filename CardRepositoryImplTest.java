import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardRepositoryImplTest {

  private static final Integer MAX_ACTIVE_CARDS = 10;


  private final CardRepository cardRepository;

  public CardRepositoryImplTest() {

    this.cardRepository = new CardRepositoryImpl();
  }

  @Test
  public void testAddNewCard_ThresholdNotExceeded() {

    final String cardId = "3437150912161415";
    Card card = new Card(cardId);

    cardRepository.addCard(card);

    Collection<Card> activeCards = cardRepository.listActiveCards();
    Assertions.assertEquals(1, activeCards.size());
    Assertions.assertEquals(cardId, activeCards.iterator().next().getId());
  }

  @Test
  public void testTransactionReceived_Online_FoundCard() {

    final Card usedCard = new Card("3437150912161415", ZonedDateTime.now().minusMinutes(1));
    cardRepository.addCard(usedCard);

    final ZonedDateTime transactionExecutedOn = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
    Transaction t = new Transaction(UUID.randomUUID(), transactionExecutedOn, usedCard);

    Assertions.assertTrue(cardRepository.receiveTransaction(t));
    Assertions.assertTrue(usedCard.getLastUsedOn().isAfter(transactionExecutedOn));
  }

  @Test
  public void testAddNewCard_ThresholdExceeded() {

    final List<Card> existingCards = prepareExistingActiveCardsOrderedByLastUsedOnDesc();
    existingCards.forEach(cardRepository::addCard);

    Card lastUsed = existingCards.get(MAX_ACTIVE_CARDS - 1);

    Card card = new Card("4437150912161459");

    cardRepository.addCard(card);

    Collection<Card> activeCards = cardRepository.listActiveCards();
    Assertions.assertEquals(MAX_ACTIVE_CARDS, activeCards.size());
    Assertions.assertTrue(activeCards.contains(card));
    Assertions.assertFalse(activeCards.contains(lastUsed));
    Assertions.assertTrue(lastUsed.getStatus() == CardStatus.BLOCKED);
  }

  @Test
  public void testTransactionReceived_Offline_FoundCard() {

    final Card usedCard = new Card("3437150912161415", ZonedDateTime.now().minusMinutes(1));
    cardRepository.addCard(usedCard);

    final ZonedDateTime transactionExecutedOn = ZonedDateTime.now().minusHours(1L).withZoneSameInstant(ZoneOffset.UTC);
    Transaction t = new Transaction(UUID.randomUUID(), transactionExecutedOn, usedCard);

    Assertions.assertTrue(cardRepository.receiveTransaction(t));
    Assertions.assertTrue(usedCard.getLastUsedOn().isAfter(transactionExecutedOn));
  }

  private List<Card> prepareExistingActiveCardsOrderedByLastUsedOnDesc() {
    return List.of(
        new Card("3437150912161415", ZonedDateTime.now().minusMinutes(1)),
        new Card("3437150912161416", ZonedDateTime.now().minusMinutes(2)),
        new Card("3437150912161417", ZonedDateTime.now().minusMinutes(3)),
        new Card("3437150912161418", ZonedDateTime.now().minusMinutes(4)),
        new Card("3437150912161419", ZonedDateTime.now().minusMinutes(5)),
        new Card("3437150912161420", ZonedDateTime.now().minusMinutes(6)),
        new Card("3437150912161429", ZonedDateTime.now().minusMinutes(7)),
        new Card("3437150912161439", ZonedDateTime.now().minusMinutes(8)),
        new Card("3437150912161449", ZonedDateTime.now().minusMinutes(9)),
        new Card("3437150912161459", ZonedDateTime.now().minusMinutes(10))
        );
  }


}
