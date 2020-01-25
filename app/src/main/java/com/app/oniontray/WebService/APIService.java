package com.app.oniontray.WebService;

import com.app.oniontray.Models.PaypalRes;
import com.app.oniontray.PayPalModule.OnlinePayRes;
import com.app.oniontray.PayPalModule.PayPalPaymentRes;
import com.app.oniontray.RequestModels.AddFavourites;
import com.app.oniontray.RequestModels.AddToCart;
import com.app.oniontray.RequestModels.Addaddress;
import com.app.oniontray.RequestModels.AddressListing;
import com.app.oniontray.RequestModels.AddressType;
import com.app.oniontray.RequestModels.AutoDetectLocation;
import com.app.oniontray.RequestModels.CancelOrder;
import com.app.oniontray.RequestModels.CartCount;
import com.app.oniontray.RequestModels.CategoriesList;
import com.app.oniontray.RequestModels.CheckDeliveryAddress;
import com.app.oniontray.RequestModels.CheckReviewReq;
import com.app.oniontray.RequestModels.CityRes;
import com.app.oniontray.RequestModels.Cooperatives;
import com.app.oniontray.RequestModels.CountryList;
import com.app.oniontray.RequestModels.CreditWalletReq;
import com.app.oniontray.RequestModels.CurrConvRes;
import com.app.oniontray.RequestModels.DeleteAddress;
import com.app.oniontray.RequestModels.DriverLocDetRes;
import com.app.oniontray.RequestModels.FaceBookRegReq;
import com.app.oniontray.RequestModels.FaceBookReq;
import com.app.oniontray.RequestModels.FeaturedStoresList;
import com.app.oniontray.RequestModels.FilterSettings;
import com.app.oniontray.RequestModels.ForgotPassword;
import com.app.oniontray.RequestModels.GPlusCheckReq;
import com.app.oniontray.RequestModels.GustCheckOutReq;
import com.app.oniontray.RequestModels.Language;
import com.app.oniontray.RequestModels.LocListApiResp;
import com.app.oniontray.RequestModels.Location;
import com.app.oniontray.RequestModels.LocationOutlet;
import com.app.oniontray.RequestModels.LocationbasedCity;
import com.app.oniontray.RequestModels.Login;
import com.app.oniontray.RequestModels.MyCart;
import com.app.oniontray.RequestModels.MyFavourites;
import com.app.oniontray.RequestModels.MyOrdRetOrdRes;
import com.app.oniontray.RequestModels.MyOrders;
import com.app.oniontray.RequestModels.NotifiDeleteResp;
import com.app.oniontray.RequestModels.NotifiListResp;
import com.app.oniontray.RequestModels.Offerlist;
import com.app.oniontray.RequestModels.OffersRespReq;
import com.app.oniontray.RequestModels.OfflinePayment;
import com.app.oniontray.RequestModels.OrderDetail;
import com.app.oniontray.RequestModels.Outlet;
import com.app.oniontray.RequestModels.PassWordUpdResponse;
import com.app.oniontray.RequestModels.PaymentGateWayResp;
import com.app.oniontray.RequestModels.ProcToCheck;
import com.app.oniontray.RequestModels.ProductList;
import com.app.oniontray.RequestModels.ProfImgUpdate;
import com.app.oniontray.RequestModels.ProfUpdResponse;
import com.app.oniontray.RequestModels.PromoCode;
import com.app.oniontray.RequestModels.PromotionReq;
import com.app.oniontray.RequestModels.ReOrderReq;
import com.app.oniontray.RequestModels.RegNewOTPReq;
import com.app.oniontray.RequestModels.Review;
import com.app.oniontray.RequestModels.SendFeedBackReq;
import com.app.oniontray.RequestModels.SendOTP;
import com.app.oniontray.RequestModels.Signup;
import com.app.oniontray.RequestModels.SocUserCredReqResp;
import com.app.oniontray.RequestModels.StoProdBanner;
import com.app.oniontray.RequestModels.StoProdVendorInfo;
import com.app.oniontray.RequestModels.StoRettingRes;
import com.app.oniontray.RequestModels.StoreList;
import com.app.oniontray.RequestModels.UserProfDet;
import com.app.oniontray.RequestModels.VendorDetailForMyCart;
import com.app.oniontray.RequestModels.VisitorsOtpReq.OtpReq;
import com.app.oniontray.RequestModels.VisitorsVerifyOtp.VerifyOtpReq;
import com.app.oniontray.RequestModels.WalPaymReq;
import com.app.oniontray.RequestModels.WalletReq;
import com.app.oniontray.RequestModels.cityList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface APIService {


    @GET("languages")
    Call<Language> getLanguage();


    @GET("getlocation/{language}")
    Call<Location> getLoction(@Path("language") String user);


    @FormUrlEncoded
    @POST("payment_gateway_list")
    Call<PaymentGateWayResp> paymentGatewayCall(@Field("language") String language);


    @FormUrlEncoded
    @POST("categorylist")
    Call<CategoriesList> get_categories_list(@Field("language") String language,
                                             @Field("type") String type);


    @FormUrlEncoded
    @POST("store_featurelist_mob")
    Call<FeaturedStoresList> get_features_store_list(@Field("language") String language,
                                                     @Field("type") String type);


    @FormUrlEncoded
    @POST("store_outlet_list")
    Call<Outlet> get_store_outlet_list(@Field("language") String lang_code,
                                       @Field("store_id") String store_id,
                                       @Field("city_id") String city_id,
                                       @Field("location") String location);


    @FormUrlEncoded
    @POST("location_outlet")
    Call<LocationOutlet> get_location_outlet_list(@Field("latitude") String latitude,
                                                  @Field("longitude") String longitude,
                                                  @Field("language") String language);


    @FormUrlEncoded
    @POST("login_user")
    Call<Login> login_user(@Field("email") String email,
                           @Field("password") String password,
                           @Field("login_type") String login_type,
                           @Field("user_type") String user_type,
                           @Field("device_id") String deviceid,
                           @Field("device_token") String token,
                           @Field("language") String lang_code);


    @FormUrlEncoded
    @POST("signup_fb_user")
    Call<FaceBookRegReq> facebook_signup_user(@Field("facebook_id") String facebook_id,
                                              @Field("email") String email,
                                              @Field("name") String name,
                                              @Field("first_name") String first_name,
                                              @Field("last_name") String last_name,
                                              @Field("gender") String gender,
                                              @Field("image_url") String image_url,
                                              @Field("device_id") String device_id,
                                              @Field("device_token") String device_token,
                                              @Field("language") String language,
                                              @Field("user_type") String user_type,
                                              @Field("login_type") String login_type,
                                              @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("signup_gplus_user")
    Call<Login> login_with_google_plus(@Field("language") String language,
                                       @Field("gplus_id") String gplusID,
                                       @Field("gplus_token") String gplus_token,
                                       @Field("email") String email,
                                       @Field("name") String name,
                                       @Field("user_type") String user_type,
                                       @Field("first_name") String first_name,
                                       @Field("last_name") String last_name,
                                       @Field("login_type") String login_type,
                                       @Field("device_id") String device_id,
                                       @Field("device_token") String device_token,
                                       @Field("image_url") String image_url,
                                       @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("signup_user")
    Call<Signup> signup_user_without_prof_img_call(@Field("first_name") String first_name,
                                                   @Field("last_name") String last_name,
                                                   @Field("email") String email,
                                                   @Field("password") String password,
                                                   @Field("phone") String phone,
                                                   @Field("gender") String gender,
                                                   @Field("terms_condition") String terms_condition,
                                                   @Field("login_type") String login_type,
                                                   @Field("language") String language,
                                                   @Field("guest_type") String guest_type,
                                                   @Field("user_type") String user_type,
                                                   @Field("device_id") String deviceid,
                                                   @Field("device_token") String token);

    @Multipart
    @POST("signup_user")
    Call<Signup> Sign_up_with_prof_img_call(@Part("first_name") RequestBody first_name,
                                            @Part("last_name") RequestBody last_name,
                                            @Part("email") RequestBody email,
                                            @Part("password") RequestBody password,
                                            @Part("phone") RequestBody phone,
                                            @Part("gender") RequestBody gender,
                                            @Part("terms_condition") RequestBody terms_condition,
                                            @Part("login_type") RequestBody login_type,
                                            @Part("language") RequestBody language,
                                            @Part("device_id") RequestBody device_id,
                                            @Part("device_token") RequestBody device_token,
                                            @Part MultipartBody.Part file);


    @POST("get_coperatives")
    Call<Cooperatives> get_coperatives();


    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgotPassword> forgot_password(@Field("email") String email,
                                         @Field("language") String lang_code);


    @FormUrlEncoded
    @POST("store_list")
    Call<StoreList> get_home_page_storeList(@Field("location") String location,
                                            @Field("city") String city,
                                            @Field("language") String language,
                                            @Field("open_restaurant") String openRestaurant,
                                            @Field("open_offer") String openOffer,
                                            @Field("sortby") String sortby,
                                            @Field("orderby") String orderby,
                                            @Field("p_methods") String p_methods,
                                            @Field("cuisine_ids") String cuisineID,
                                            @Field("delivery_free") String delivery_free);


    @GET("store_banner")
    Call<StoProdBanner> getStoProdBanner();


    @FormUrlEncoded
    @POST("store_info_mob")
    Call<StoProdVendorInfo> get_store_Prod_VenderInfo(@Field("language") String language,
                                                      @Field("outlet_id") String store_id,
                                                      @Field("city") String city,
                                                          @Field("location") String location,
                                                      @Field("user_id") String user_id,
                                                      @Field("token") String token);


    @FormUrlEncoded
    @POST("store_product_mob")
    Call<ProductList> get_store_product_list(@Field("language") String language,
                                             @Field("user_id") String user_id,
                                             @Field("token") String user_token,
                                             @Field("store_id") String store_id,
                                             @Field("category_id") String category_id,
//                                             @Field("sub_category_id") String token,
                                             @Field("outlet_id") String outlet_id,
                                             @Field("product_name") String product_name);


    @FormUrlEncoded()
    @POST("favourites")
    Call<MyFavourites> get_favourites_list(@Field("language") String language,
                                           @Field("user_id") String user_id,
                                           @Field("token") String token,
                                           @Field("type") String type,
                                           @Field("city_id") String cityID,
                                           @Field("location_id") String locationID);


    @FormUrlEncoded
    @POST("addto_favourite")
    Call<AddFavourites> addto_favourite(@Field("user_id") String user_id,
                                        @Field("vendor_id") String vendor_id,
                                        @Field("outlet_id") String outlet_id,
                                        @Field("token") String token,
                                        @Field("language") String language);


    @FormUrlEncoded
    @POST("getoffers_list")
    Call<Offerlist> getoffers_list(@Field("language") String lang,
                                   @Field("user_id") String user_id,
                                   @Field("token") String token);


    @FormUrlEncoded
    @POST("notification-list")
    Call<NotifiListResp> get_notification_list_call(@Field("user_id") String user_id,
                                                    @Field("token") String token,
                                                    @Field("language") String language);


    @FormUrlEncoded
    @POST("delete-notification")
    Call<NotifiDeleteResp> notification_list_delete_call(@Field("user_id") String user_id,
                                                         @Field("token") String token,
                                                         @Field("language") String language,
                                                         @Field("notification_id") String notification_id);


    @FormUrlEncoded
    @POST("store_address")
    Call<Addaddress> store_address(@Field("user_id") String user_id,
                                   @Field("token") String token,
                                   @Field("language") String language,
                                   @Field("address type") String address_type,
                                   @Field("latitude") String latitude,
                                   @Field("longtitude") String longtitude,
                                   @Field("address") String address,
                                   @Field("flat_number") String flat_number,
                                   @Field("landmark") String landmark,
                                   @Field("country_id") String country_id,
                                   @Field("city_id") String cityID,
                                   @Field("location_id") String locationID);


    @POST("address_type")
    Call<AddressType> address_type();


    @FormUrlEncoded
    @POST("get_address")
    Call<AddressListing> get_address(@Field("user_id") String user_id,
                                     @Field("language") String language,
                                     @Field("user_token") String token);


    @FormUrlEncoded
    @POST("delete_address")
    Call<DeleteAddress> delete_address(@Field("language") String language,
                                       @Field("user_id") String user_id,
                                       @Field("address id") String address_id);

    @FormUrlEncoded
    @POST("distance-address")
    Call<CheckDeliveryAddress> check_delivery_address(@Field("outlet_id") String outletID,
                                                      @Field("address_id") String locationID,
                                                      @Field("language") String language,
                                                      @Field("token") String token);


    @FormUrlEncoded
    @POST("orders")
    Call<MyOrders> order_list(@Field("user_id") String user_id,
                              @Field("language") String language,
                              @Field("token") String token);


    @FormUrlEncoded
    @POST("store_review")
    Call<Review> store_review(@Field("store_id") String storeid,
                              @Field("outlet_id") String outletid,
                              @Field("language") String language);


    @FormUrlEncoded
    @POST("add_to_cart")
    Call<AddToCart> add_to_cart(@Field("language") String language,
                                @Field("vendors_id") String vendor_id,
                                @Field("user_id") String user_id,
                                @Field("product_id") String product_id,
                                @Field("qty") String quantity,
                                @Field("outlet_id") String outlet_id,
                                @Field("token") String token);


    @FormUrlEncoded
    @POST("get_cart")
    Call<MyCart> my_cart_call(@Field("language") String language,
                              @Field("user_id") String user_id,
                              @Field("token") String user_token);


    @FormUrlEncoded
    @POST("outlet-details")
    Call<VendorDetailForMyCart> getOutletDetails(@Field("language") String language,
                                                 @Field("vendor_id") String vendorID,
                                                 @Field("outlet_id") String outletID,
                                                 @Field("city") String city,
                                                 @Field("location") String location);


    @FormUrlEncoded
    @POST("update_cart")
    Call<MyCart> update_cart_call(@Field("language") String language,
                                  @Field("user_id") String user_id,
                                  @Field("token") String user_token,
                                  @Field("qty") String quantity,
                                  @Field("cart_detail_id") String cart_detail,
                                  @Field("cart_id") String cart_id);


    @FormUrlEncoded
    @POST("order_detail")
    Call<OrderDetail> Order_detail_call(@Field("language") String language,
                                        @Field("order_id") String order_id,
                                        @Field("user_id") String user_id,
                                        @Field("city_id") String city_id,
                                        @Field("location_id") String location_id,
                                        @Field("token") String user_token);


    @FormUrlEncoded
    @POST("cancel_order")
    Call<CancelOrder> cancel_order_call(@Field("language") String language,
                                        @Field("order_id") String order_id,
                                        @Field("user_id") String user_id,
                                        @Field("token") String token);


    @FormUrlEncoded
    @POST("re_order")
    Call<ReOrderReq> re_order_Request_call_method(@Field("order_id") String order_id,
                                                  @Field("user_id") String user_id,
                                                  @Field("language") String language,
                                                  @Field("type") String type,
                                                  @Field("token") String token);


    @FormUrlEncoded
    @POST("return_order")
    Call<MyOrdRetOrdRes> return_order_call(@Field("user_id") String user_id,
                                           @Field("order_id") String order_id,
                                           @Field("token") String token,
                                           @Field("language") String language,
                                           @Field("return_reason") String return_reason,
                                           @Field("comments") String comments,
                                           @Field("vendor_name") String vendor_name);


    @FormUrlEncoded
    @POST("user_rating")
    Call<StoRettingRes> update_store_review_call(@Field("vendor_id") String vendor_id,
                                                 @Field("outlet_id") String outlet_id,
                                                 @Field("user_id") String user_id,
                                                 @Field("order_id") String order_id,
                                                 @Field("starrating") String starrating,
                                                 @Field("comments") String comments);


    @FormUrlEncoded
    @POST("checkout_detail")
    Call<ProcToCheck> checkout_detail_call(@Field("user_id") String user_id,
                                           @Field("language") String language);

    @FormUrlEncoded
    @POST("checkout_detail")
    Call<ProcToCheck> checkout_detail(@Field("user_id") String user_id,
                                      @Field("language") String language,
                                      @Field("cart") String cart,
                                      @Field("vendors_id") String vendorId);


    @FormUrlEncoded
    @POST("cart_count")
    Call<CartCount> cart_count_call(@Field("user_id") String user_id,
                                    @Field("language") String language,
                                    @Field("token") String token);


    @FormUrlEncoded
    @POST("send_otp")
    Call<SendOTP> sendOTP(@Field("token") String token,
                          @Field("user_id") String user_id,
                          @Field("language") String language,
                          @Field("otp_option") String otpOption);


    @FormUrlEncoded
    @POST("check_otp")
    Call<SendOTP> checkOTP(@Field("token") String token,
                           @Field("user_id") String user_id,
                           @Field("language") String language,
                           @Field("otp") String otp);


    @FormUrlEncoded
    @POST("offline_payment")
    Call<OfflinePayment> offlinePayment(@Field("token") String token,
                                        @Field("user_id") String user_id,
                                        @Field("language") String language,
                                        @Field("payment_array") String paymentArray);


    @FormUrlEncoded
    @POST("online_payment")
    Call<OnlinePayRes> onlinnePayment(@Field("token") String token,
                                      @Field("user_id") String user_id,
                                      @Field("language") String language,
                                      @Field("payment_array") String paymentArray,
                                      @Field("payment_params") String payment_params);


    @GET("getcity/{lang}")
    Call<cityList> CityList(@Path("lang") String lang);


    @FormUrlEncoded
    @POST("city-list")
    Call<CityRes> LocationList(@Field("language") String language,
                               @Field("country_id") String country_id);


    @FormUrlEncoded
    @POST("location-list")
    Call<LocListApiResp> CityBasedLocationList(@Field("language") String language,
                                               @Field("country_id") String country_id,
                                               @Field("city_id") String city_id);


    @FormUrlEncoded
    @POST("locationlist")
    Call<LocationbasedCity> locationBasedCityList(@Field("language") String language,
                                                  @Field("city_id") String city_id);


    @FormUrlEncoded
    @POST("country-list")
    Call<CountryList> country_list(@Field("language") String language);


    @FormUrlEncoded
    @POST("update_promocode")
    Call<PromoCode> promo_code_call(@Field("promo_code") String promocode,
                                    @Field("user_id") String user_id,
                                    @Field("outlet_id") String outlet_id,
                                    @Field("language") String language,
                                    @Field("token") String user_token,
                                    @Field("total") String total);


    @FormUrlEncoded
    @POST("update_password")
    Call<PassWordUpdResponse> update_password(@Field("token") String token,
                                              @Field("user_id") String user_id,
                                              @Field("old_password") String old_password,
                                              @Field("password") String password,
                                              @Field("password_confirmation") String password_confirmation,
                                              @Field("language") String language);


    @Multipart
    @POST("update_profile_image")
    Call<ProfImgUpdate> UpdateProfilePicRequest(@Part("language") RequestBody language,
                                                @Part("token") RequestBody token,
                                                @Part("user_id") RequestBody user_id,
                                                @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("update_profile")
    Call<ProfUpdResponse> update_profile(@Field("user_id") String user_id,
                                         @Field("first_name") String first_name,
                                         @Field("last_name") String last_name,
                                         @Field("phone") String phone,
                                         @Field("gender") String gender,
                                         @Field("android_device_id") String android_device_id,
                                         @Field("android_device_token") String android_device_token,
                                         @Field("language") String language,
                                         @Field("email") String email,
                                         @Field("token") String token);


    @FormUrlEncoded
    @POST("user_detail")
    Call<UserProfDet> user_Profile_Details(@Field("token") String token,
                                           @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("token")
    Call<PaypalRes> basicLogin(@Field("grant_type") String grantType);


    @GET("payment/{payment_id}")
    Call<PayPalPaymentRes> getPaymentDetails(@Path("payment_id") String user);


    @FormUrlEncoded
    @POST("currency-converter")
    Call<CurrConvRes> CurrentyConverterRequest(@Field("user_id") String user_id,
                                               @Field("token") String token,
                                               @Field("currency_amount") String currency_amount,
                                               @Field("from_currency") String fromcurrency);


    @FormUrlEncoded
    @POST("filter-option-list")
    Call<FilterSettings> filter_settings_api(@Field("user_id") String userID,
                                             @Field("language") String language);


    @FormUrlEncoded
    @POST("current-location")
    Call<AutoDetectLocation> auto_detect_location(@Field("language") String language,
                                                  @Field("latitude") String latitude,
                                                  @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("contact-us")
    Call<SendFeedBackReq> SendFeedBackReqMethod(@Field("name") String name,
                                                @Field("mobile_number") String mobile_number,
                                                @Field("email") String email,
                                                @Field("city") String city,
                                                @Field("enquery_type") String enquery_type,
                                                @Field("message") String message,
                                                @Field("language") String language);


    @FormUrlEncoded
    @POST("check-social-login-id")
    Call<FaceBookReq> CheckFaceBookLoginReqMethod(@Field("language") String language,
                                                  @Field("user_type") String user_type,
                                                  @Field("facebook_id") String facebook_id);


    @FormUrlEncoded
    @POST("check-social-login-id")
    Call<GPlusCheckReq> CheckGooglePlusLoginReqMethod(@Field("language") String language,
                                                      @Field("user_type") String user_type,
                                                      @Field("gplus_id") String facebook_id);


    @FormUrlEncoded
    @POST("check-social-user-credientials")
    Call<SocUserCredReqResp> CheckSocialUserCredientialsReqMethod(@Field("language") String language,
                                                                  @Field("user_type") String user_type,
                                                                  @Field("email") String email,
                                                                  @Field("phone") String phone);


    @FormUrlEncoded
    @POST("getoffers_list")
    Call<OffersRespReq> GetPromotionsOffersList(@Field("language") String language,
                                                @Field("user_id") String user_type,
                                                @Field("token") String facebook_id);


    @FormUrlEncoded
    @POST("getpromotion_list")
    Call<PromotionReq> GetPromotionsList(@Field("language") String language,
                                         @Field("city") String city,
                                         @Field("location") String location);


//     [first_name] => Boopathi
//    [last_name] => Saravanan
//    [email] => Boopathi@mailinator.com
//    [mobile] => +919597141607
//            [address] => Hamad Yousif Bin Hussain Al-Roumi St, Kuwait City, Kuwait
//    [latitude] => 29.3167227
//            [longitude] => 48.002007100000014
//            [flat_number] => 147/11
//            [landmark] =>
//            [city_id] => 21
//            [location_id] => 75
//            [address_type] => 1
//            [language] => 1
//            [payment_array] =>
//    [guest_type] => 1
//            [login_type] => 1


    @FormUrlEncoded
    @POST("guest-offline-payment")
    Call<GustCheckOutReq> getExpressCheckoutRequest(@Field("language") String language,
                                                    @Field("first_name") String first_name,
                                                    @Field("last_name") String last_name,
                                                    @Field("email") String email,
                                                    @Field("mobile") String mobile,
                                                    @Field("city_id") String city_id,
                                                    @Field("location_id") String location_id,
                                                    @Field("address") String address,
                                                    @Field("flat_number") String flat_number,
                                                    @Field("landmark") String landmark,
                                                    @Field("latitude") String latitude,
                                                    @Field("longitude") String longitude,
                                                    @Field("address_type") String address_type,
                                                    @Field("payment_array") String payment_array,
                                                    @Field("guest_type") String guest_type,
                                                    @Field("login_type") String login_type,
                                                    @Field("device_id") String deviceid,
                                                    @Field("device_token") String token);


    @FormUrlEncoded
    @POST("verify-otp-new-mobile")
    Call<SendOTP> RegOTPVerifyReq(@Field("language") String language,
                                  @Field("user_id") String user_id,
                                  @Field("phone") String phone,
                                  @Field("type") String type,
                                  @Field("password") String password,
                                  @Field("otp") String otp);

    @FormUrlEncoded
    @POST("verify-otp-new-mobile")
    Call<SendOTP> SignUpOTPVerifyReq(@Field("language") String language,
                                     @Field("user_id") String user_id,
                                     @Field("phone") String phone,
                                     @Field("otp") String otp);


    @FormUrlEncoded
    @POST("send-otp-new-mobile")
    Call<RegNewOTPReq> RegReSendOTPReq(@Field("language") String language,
                                       @Field("user_id") String user_id,
                                       @Field("phone") String phone);


    @FormUrlEncoded
    @POST("send-otp-profile")
    Call<RegNewOTPReq> ProfEditReSendOTPReq(@Field("language") String language,
                                            @Field("user_id") String user_id,
                                            @Field("first_name") String first_name,
                                            @Field("last_name") String last_name,
                                            @Field("phone") String phone,
                                            @Field("gender") String gender,
                                            @Field("android_device_id") String android_device_id,
                                            @Field("android_device_token") String android_device_token,
                                            @Field("email") String email,
                                            @Field("token") String token);


    @FormUrlEncoded
    @POST("user_wallet")
    Call<WalletReq> UserWalletRequest(@Field("language") String language,
                                      @Field("user_id") String user_id,
                                      @Field("token") String token);


    @FormUrlEncoded
    @POST("credit-wallet")
    Call<CreditWalletReq> AddCreditWalletRequest(@Field("language") String language,
                                                 @Field("user_id") String user_id,
                                                 @Field("token") String token,
                                                 @Field("payment_array") String payment_array);


    @FormUrlEncoded
    @POST("wallet-payment")
    Call<WalPaymReq> WalletPaymentReq(@Field("language") String language,
                                      @Field("user_id") String user_id,
                                      @Field("token") String token,
                                      @Field("payment_array") String payment_array,
                                      @Field("payment_params") String payment_params);


    @FormUrlEncoded
    @POST("check-user-last-order")
    Call<CheckReviewReq> CheckUserLatestOrderReview(@Field("language") String language,
                                                    @Field("user_id") String user_id,
                                                    @Field("token") String token);


    @FormUrlEncoded
    @POST("order-driver-location-details")
    Call<DriverLocDetRes> Order_driver_loc_req_method(@Field("language") String language,
                                                      @Field("order_id") String order_id,
                                                      @Field("user_id") String user_id,
                                                      @Field("token") String user_token);


    @FormUrlEncoded
    @POST("guest_update_promocode")
    Call<PromoCode> guestUpdatePromoCodeCall(@Field("language") String language,
                                             @Field("email") String email,
                                             @Field("mobile") String mobile,
                                             @Field("outlet_id") String outlet_id,
                                             @Field("total") String total,
                                             @Field("promo_code") String promo_code);


    @FormUrlEncoded
    @POST("guest_online_payment")
    Call<OnlinePayRes> guestPayPalOnlinePayment(@Field("language") String language,
                                                @Field("first_name") String first_name,
                                                @Field("last_name") String last_name,
                                                @Field("email") String email,
                                                @Field("mobile") String mobile,
                                                @Field("address") String address,
                                                @Field("flat_number") String flat_number,
                                                @Field("landmark") String landmark,
                                                @Field("latitude") String latitude,
                                                @Field("longitude") String longitude,
                                                @Field("city_id") String city_id,
                                                @Field("location_id") String location_id,
                                                @Field("address_type") String address_type,
                                                @Field("guest_type") String guest_type,
                                                @Field("login_type") String login_type,
                                                @Field("device_id") String device_id,
                                                @Field("device_token") String device_token,
                                                @Field("payment_array") String paymentArray,
                                                @Field("payment_params") String payment_params);
    @FormUrlEncoded
    @POST("guest_send_otp")
    Call<OtpReq> guestOtpRequset(@Field("language") String language,
                                 @Field("guest_email") String guestEmail,
                                 @Field("guest_mobile") String guestMobile,
                                 @Field("guest_first_name") String guestFirstName,
                                 @Field("guest_last_name") String guestLastName,
                                 @Field("otp_option") String otpOption);


    @FormUrlEncoded
    @POST("guest_check_otp")
    Call<VerifyOtpReq> guestVerifyOtp(@Field("language") String language,
                                      @Field("guest_mobile") String guest_mobile,
                                      @Field("otp") String otp);

    @FormUrlEncoded
    @POST("razor_online_payment")
    Call<OnlinePayRes> razorpayonlinnePayment(@Field("token") String token,
                                              @Field("user_id") String user_id,
                                              @Field("language") String language,
                                              @Field("payment_array") String paymentArray);
}
