# GoldenOwlAsia
## webdev-intern-assignment
<p>
GShoes project on Android.
</p>

<p align="center">
<img src="./resources/Peek%202022-04-25%2020-56.gif" width="40%" />
</p>
<br>

## Environment
- IntelliJ IDEA 2022.1 (Community Edition)
- Android 11

## Dependencies / Libraries
- com.aurelhubert:ahbottomnavigation:2.3.4
- com.squareup.picasso:picasso:2.71828

## How it work?
- This project uses **PageViewer** to display 2 Fragment in slides: _ShopFragment_ and _CartFragment_
- I use **ViewModel** to communicate between 2 Fragment, which holds 2 properties:
ShopRepo , CartRepo.
  - ShopRepo manages adding a product to cart. When a product deleted in cart, ShopRepo re-enable that product to be added to cart again.
  - CartRepo manages how to add, remove, delete a product in ListCartProduct. It also auto calculate the total price when any change applied.
