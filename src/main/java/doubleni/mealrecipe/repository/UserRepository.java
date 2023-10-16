package doubleni.mealrecipe.repository;

import doubleni.mealrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailAndAndPassword(String email, String password);





    /*
    *  // 사용자의 이메일로 사용자 찾기
    User findByEmail(String email);

    // 사용자의 전화번호로 사용자 찾기
    User findByPhone(String phone);

    // 사용자의 주소 목록 가져오기
    List<UserAddress> findByUserIdAndSelected(int userId, boolean isSelected);

    // 사용자의 주문 목록 가져오기
    List<Order> findByUserIdAndDeliveryStatus(int userId, int deliveryStatus);

    // 사용자의 영수증 목록 가져오기
    List<Receipt> findByUserIdAndOrderOrderId(int userId, String orderId);

    // 사용자의 주소 상세 정보 가져오기
    UserAddressDetail findByUserIdAndUserAddressId(int userId, int userAddressId);

    // 사용자의 주소 정보 가져오기
    List<UserAddressInformation> findByUserId(int userId);

    // 사용자 주소 상세 정보 수정
    @Modifying
    @Query("update UserAddress ua set ua.detailAddress = :detailAddress, ua.wayGuide = :wayGuide, ua.kind = :kind, ua.addressAlias = :addressAlias where ua.user.userId = :userId and ua.userAddressId = :userAddressId")
    @Transactional
    int modifyUserAddressDetail(@Param("userId") int userId, @Param("userAddressId") int userAddressId, @Param("detailAddress") String detailAddress, @Param("wayGuide") String wayGuide, @Param("kind") int kind, @Param("addressAlias") String addressAlias);

    // 사용자 주소 추가
    @Modifying
    @Query(value = "insert into user_address (user_id, address_name, doro_name_address, detail_address, way_guide, address_alias, kind) VALUES (:userId, :addressName, :doroNameAddress, :detailAddress, :wayGuide, :addressAlias, :kind)", nativeQuery = true)
    @Transactional
    int createUserAddress(@Param("userId") int userId, @Param("addressName") String addressName, @Param("doroNameAddress") String doroNameAddress, @Param("detailAddress") String detailAddress, @Param("wayGuide") String wayGuide, @Param("addressAlias") String addressAlias, @Param("kind") int kind);

    // 사용자 즐겨찾기 목록 가져오기
    List<UserFavorite> findByUserId(int userId);

    // 사용자 즐겨찾기 삭제
    @Modifying
    @Query("delete from UserFavorite uf where uf.user.userId = :userId and uf.restaurant.restaurantId = :restaurantId")
    int deleteUserFavorite(@Param("userId") int userId, @Param("restaurantId") int restaurantId);

    // 사용자 즐겨찾기 추가
    @Modifying
    @Query(value = "INSERT INTO user_favorite (restaurant_id, user_id) VALUES (:restaurantId, :userId)", nativeQuery = true)
    @Transactional
    int createUserFavorite(@Param("restaurantId") int restaurantId, @Param("userId") int userId);

    // 사용자의 배송 주소 정보 가져오기 (Cart에 필요한 정보)
    UserAddressCartInfo findByUserId(int userId);
    *
    *
    * */
}
