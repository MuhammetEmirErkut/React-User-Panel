package FinalProject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Comminicate {

    public static void main(String[] args) {
    	
        Center center = new Center();

        Car car1 = new Car(center);
        center.register(car1);

        Cup cup1 = new Cup(center);
        center.register(cup1);

        Cat cat1 = new Cat(center);
        center.register(cat1);

        Cloud cloud1 = new Cloud(center);
        center.register(cloud1);

        center.textMessage("1:2:Are you here?");

        center.textMessage("1:all:Hi everyone");

        center.textMessage("3:1:quete");
    }
}

class IdGenerator {
    private int currentIndex;//Burada her çağırıldığında 1'er şekilde artan bir sınıf tasarladım

    IdGenerator() {
        currentIndex = 0;
    }

    int getNextId() {
        return currentIndex++;
    }
}

class Center {
    private Map<Integer, MessageReceiver> messageReceivers;//Burada oluşturulacak ve saklanacak nesnelerin idsine kolayca ulaşabilmek için Map yapısını kullandım
    private IdGenerator idGenerator;//Bu yapı key ve value şeklinde ayrıldığı için kolaylık döngüler ve kontrol sağladığım yerlerde fazlasıyla işime yaradı
    
    IdGenerator getIdGenerator() {//Bu kısmı iletişimci sınıflarının içerisinde IdGenerator içerisindeki getNextId metoduna ulaşabilmek için yazdım. 
        return idGenerator;
    }
    public Center() {
        messageReceivers = new HashMap<>();
        idGenerator = new IdGenerator();
    }

    void register(MessageReceiver messageReceiver) {//Bu kısımda alıcılar için tasarladığım arayüzü kullanarak öncelikle alıcının id sini kullanarak koleksiyona ekliyoruz
        int objectId = messageReceiver.getId();//ardından alıcı için bir dosya oluşturuyoruz
        messageReceivers.put(objectId, messageReceiver);
        messageReceiver.createTextFile();
        
            MessageReceiver sender = messageReceivers.get(objectId);//Mesaj göndermek arayüz sayesinde olduğu için arayüzü ait bir değişkeni gönderenin idsini kullanarak koleksiyon içerisinden çekiyoruz

            for (MessageReceiver receiver : messageReceivers.values()) {//messageReceviers adlı Map içerisindeki nesnelerin üzerdinde bir döngü oluşturdum
                if (sender != null) {//sender dolu olduğu sürece önce alıntıyı alıyoruz arayüz alıcılığıyla, ardından mesaj gönderiyoruz
                    String quete = sender.receiveMessage(receiver, null);
                    sendMessage(sender, receiver, quete);
                } else {
                    System.out.println("Sender object not found.");
                }
            }
        
    }

    void remove(MessageReceiver messageReceiver) {//Buradada aynı şeyi yapıyoruz ama koleksiyondan çıkartmamız için tasarladım burada
        int objectId = messageReceiver.getId();
        messageReceivers.remove(objectId);
    }

    void textMessage(String message) {//Bu ksım biraz karışık elimden geldiğince ilk yazdığım halden daha optimize hale getirdim
        String[] parts = message.split(":");//Burada mesajı 3'e bölüyoruz
        if (parts.length != 3) {
            System.out.println("Invalid message format.");
            return;
        }

        int senderId = Integer.parseInt(parts[0]);//Gönderen nesnenin id'sini değişkene atıyoruz metod ve arayüzlerde kullanabilmek için

        if (parts[2].equals("quete")) {//Bu kısım alıntı için olan kısım, alıntı için olmayan kısımda doğrudan mesaj kısmından alıyoruz ama alıntı olduğunda arayüzü kullanarak sınıfların içerisinden textleri alıyoruz
            if (parts[1].equals("all")) {//Burada mesajı herkese gönderiyoruz mesajı

                MessageReceiver sender = messageReceivers.get(senderId);//Mesaj göndermek arayüz sayesinde olduğu için arayüzü ait bir değişkeni gönderenin idsini kullanarak koleksiyon içerisinden çekiyoruz

                for (MessageReceiver receiver : messageReceivers.values()) {//messageReceviers adlı Map içerisindeki nesnelerin üzerdinde bir döngü oluşturdum
                    if (sender != null) {//sender dolu olduğu sürece önce alıntıyı alıyoruz arayüz alıcılığıyla, ardından mesaj gönderiyoruz
                        String quete = sender.receiveQueteMessage(receiver);
                        sendMessage(sender, receiver, quete);
                    } else {
                        System.out.println("Sender object not found.");
                    }
                }
            } else {//Bu kısımlar birbirine çok yakın üst kısımda farklarından bahsettim ama tabi bu kısımda tek bir nesneye mesaj göndereceği için döngü kullanmamıza gerek kalmıyor

                MessageReceiver sender = messageReceivers.get(senderId);
                int receiverId = Integer.parseInt(parts[1]);
                MessageReceiver receiver = messageReceivers.get(receiverId);

                if (sender != null && receiver != null) {
                    String quete = sender.receiveQueteMessage(receiver);
                    sendMessage(sender, receiver, quete);
                } else {
                    System.out.println("Sender or receiver object not found.");
                }
            }
        } else {
            if (parts[1].equals("all")) {

                MessageReceiver sender = messageReceivers.get(senderId);
                String text = parts[2];

                for (MessageReceiver receiver : messageReceivers.values()) {
                    if (sender != null) {
                        sendMessage(sender, receiver, text);
                    } else {
                        System.out.println("Sender object not found.");
                    }
                }
            } else {
                String text = parts[2];
                MessageReceiver sender = messageReceivers.get(senderId);
                int receiverId = Integer.parseInt(parts[1]);
                MessageReceiver receiver = messageReceivers.get(receiverId);                

                if (sender != null && receiver != null) {
                    sendMessage(sender, receiver, text);
                } else {
                    System.out.println("Sender or receiver object not found.");
                }
            }
        }
    }

    private void sendMessage(MessageReceiver sender, MessageReceiver receiver, String message) {//Mesaj göndermek için arayüzle iletişim kurduğumuz metod
        int senderId = sender.getId();//Öncelikle gönderenin ve alıcının Idlerini alıyoruz
        int receiverId = receiver.getId();

        System.out.println("(" + receiverId + ")" + receiver.getClass().getSimpleName() + " received message from (" + senderId + ")" + sender.getClass().getSimpleName() + ": " + message);
        
        String fileName = "nesne_" + receiverId + ".txt";
        try {//Burada dosyaya gönderilen mesajın içeriği kimin gönderdiği ve kimin aldığını dosyaya yazdırıyoruz
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("(" + receiverId + ")" + receiver.getClass().getSimpleName() + " received message from (" + senderId + ")" + sender.getClass().getSimpleName() + ": " + message);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

interface MessageReceiver {
    String receiveMessage(MessageReceiver sender, String message);

    String receiveQueteMessage(MessageReceiver sender);

    int getId();

    void createTextFile();
    
    String comminicateComminicators(String message);
}

class Car implements MessageReceiver {//Bir iletişimci sınıfı
    private List<String> queteMessages;//Alıntıları tutmak için bir ArrayList
    private Random random;//alıntılardan rastgele seçim yapabilmek için random nesnesi
    private int id;//oluşturulacak her nesne için id özelliği
    private Center center;//Centerda kullanacağımız metodlar için bir center nesnesi
    private IdGenerator idgen;//getNextId metodunu çağırabilmek için IdGenerator nesnesi   

    public Car(Center center) {
        queteMessages = new ArrayList<>();
        queteMessages.add("Bir dil öğrenmek, yeni bir dünyanın kapısını aralamaktır.");
        queteMessages.add("Programlama, problem çözme sanatıdır.");
        queteMessages.add("Kodlama, insanların makinelerle konuşma şeklidir.");
        queteMessages.add("Yazılım, düşüncelerin gerçeğe dönüştüğü bir sanattır.");
        random = new Random();
        this.center = center;
        idgen = center.getIdGenerator();//idGenerator metodunu çağırabilmek adına önce idgenin IdGenerator sınıfına return ettik
        id = idgen.getNextId();//Ardından IdGenerator sınıfındaki metodu çağırdık ve böylelikle oluşacak  her nesnenin bir id'si oldu
    }

    @Override
    public void receiveMessage(MessageReceiver sender, String message) {//alıcının arayüzle iletişim kurması için tasarlanmış bir metod
        System.out.println("Car received message from " + sender.getClass().getSimpleName() + ": " + message);
    }

    @Override
    public String receiveQueteMessage(MessageReceiver sender) {//alıntıların 4'ünden 1'ini random olarak alıp döndüren bir metod
        return queteMessages.get(random.nextInt(4));
    }

    @Override
    public int getId() {//metodlarda kontrol sağlayıp nesnelerin id'sine ulaşabilmek için bir metod
        return id;
    }

    @Override
    public void createTextFile() {//Bu da oluşturulan nesneler için bir text dosyası oluşturan metod
        String fileName = "nesne_" + getId() + ".txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.close();
            System.out.println("Text file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public String comminicateComminicators(String message) {
		return message = "(" + getId() + ") Car object created";
		
	}
}
class Cup implements MessageReceiver {//Diğer sınıflar birebir aynı..
	private List<String> queteMessages;
	private Random random;
	private int id;
	private Center center;
	private IdGenerator idgen;
	
	public Cup(Center center) {
		queteMessages = new ArrayList<>();
		queteMessages.add("Bir hata yaparsan, sadece bir hata yapmış olmazsın. Aynı zamanda yeni bir şeyler denemiş olursun.");
        queteMessages.add("Bir dilde program yazmak, sadece onu bilgisayara anlatmanın bir yoludur. Asıl amacımız, diğer insanların anlayabileceği şekilde düşünmektir.");
        queteMessages.add("Programlama, mantığı ve hayal gücünü birleştirme sanatıdır.");
        queteMessages.add("İyi bir yazılım geliştirici, sorunlarınızı çözebileceğiniz bir sihirbazdır.");
        random = new Random();
        this.center = center;
        idgen = center.getIdGenerator();
        id = idgen.getNextId();
	}
	
	@Override
	public void receiveMessage(MessageReceiver sender, String message) {
		System.out.println("Cup received message from " + sender.getClass().getSimpleName() + ": " + message);
	}
	public String receiveQueteMessage(MessageReceiver sender) {
		return queteMessages.get(random.nextInt());
	}
	public int getId() {
		return id;
	}
	
	@Override
	public void createTextFile() {
		String fileName = "nesne_" + getId() + ".txt";
		
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.close();
			System.out.println("Text file created: " + fileName);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public String comminicateComminicators(String message) {
		return message = "(" + getId() + ") Cup object created";
		
	}
}
class Cat implements MessageReceiver{
	private List<String> queteMessages;
	private Random random;
	private int id;
	private Center center;
	private IdGenerator idgen;
	
	
	public Cat(Center center) {
		queteMessages = new ArrayList<>();
        queteMessages.add("Kod, notlarınızı bilmeyen gelecekteki bir sürdürücüye bıraktığınız bir mesajdır.");
        queteMessages.add("Bilgisayar bilimi, problem çözme hakkında düşünmeyi öğretir.");
        queteMessages.add("Programlama, beyin jimnastiği yapmanın en iyi yoludur.");
        queteMessages.add("Kod yazmak, bir hikaye anlatmaktır. En iyi kod, kendini açıklayan koddur.");
        random = new Random();
        this.center = center;
        idgen = center.getIdGenerator();
        id = idgen.getNextId();
	}
	
	@Override
	public void receiveMessage(MessageReceiver sender, String message) {
        System.out.println("Cat received message from " + sender.getClass().getSimpleName() + ": " + message);
	}
	@Override
	public String receiveQueteMessage(MessageReceiver sender) {
		return queteMessages.get(random.nextInt(4));
	}
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void createTextFile() {
		String fileName = "nesne_" + getId() + ".txt";
		
		try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.close();
            System.out.println("Text file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public String comminicateComminicators(String message) {
		return message = "(" + getId() + ") Cat object created";
		
	}
}
class Cloud implements MessageReceiver{
	private List<String> queteMessages;
	private Random random;
	private int id;
	private Center center;
	private IdGenerator idgen;
	
	
	
	public Cloud(Center center) {
		queteMessages = new ArrayList<>();
        queteMessages.add("Kod, notlarınızı bilmeyen gelecekteki bir sürdürücüye bıraktığınız bir mesajdır.");
        queteMessages.add("Bilgisayar bilimi, problem çözme hakkında düşünmeyi öğretir.");
        queteMessages.add("Programlama, beyin jimnastiği yapmanın en iyi yoludur.");
        queteMessages.add("Kod yazmak, bir hikaye anlatmaktır. En iyi kod, kendini açıklayan koddur.");
        random = new Random();
        this.center = center;
        idgen = center.getIdGenerator();
        id = idgen.getNextId();
	}
	
	@Override
	public void receiveMessage(MessageReceiver sender, String message) {
        System.out.println("Cloud received message from " + sender.getClass().getSimpleName() + ": " + message);
	}
	@Override
	public String receiveQueteMessage(MessageReceiver sender) {
		return queteMessages.get(random.nextInt(4));
	}
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void createTextFile() {
		String fileName = "nesne_" + getId() + ".txt";
		
		try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.close();
            System.out.println("Text file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public String comminicateComminicators(String message) {
		return message = "(" + getId() + ") Cloud object created";
		
	}
}




