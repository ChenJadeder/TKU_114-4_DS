import java.util.Objects;

class LibraryMember {
private final String memberId;
private final String name;
private final String email;
    LibraryMember(String memberId, String name, String email) {
    this.memberId = (memberId == null || memberId.trim().isBlank()) ? "UNKNOWN" : memberId.trim();
    this.name = (name == null || name.trim().isBlank()) ? "Unknown" : name.trim();
    this.email = (email == null || email.trim().isBlank()) ? "unknown@example.com" : email.trim();
    }

@Override
public String toString() {
    return "Member{id='" + memberId + "', name='" + name + "', email='" + email + "'}";
    }

@Override
public boolean equals(Object other) {
    if (this == other) return true;          // 同一參考
    if (other == null) return false;         // 對 null 應為 false
    if (getClass() != other.getClass()) return false; // 類型不同
    LibraryMember that = (LibraryMember) other;
    return Objects.equals(this.memberId, that.memberId);
    }

@Override
public int hashCode() {
    return Objects.hash(memberId);
    }
}
public class MemberEqualityPractice {
public static void main(String[] args) {
LibraryMember a = new LibraryMember("M001", "Amy", "amy@example.com");
LibraryMember b = new LibraryMember("M001", "Amy Chen", "amy.chen@example.com");
    System.out.println(a);
    System.out.println(b);
    System.out.println("a == b: " + (a == b));
    System.out.println("a.equals(b): " + a.equals(b));
    System.out.println("a.equals(null): " + a.equals(null));
    }
}