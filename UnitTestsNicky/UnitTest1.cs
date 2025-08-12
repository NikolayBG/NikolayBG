namespace UnitTestsNicky
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Add_TwoPositiveNumbers_ReturnsCorrectSum()
        {
            int Add(int a, int b) => a + b;

            int result = Add(3, 5);
            Assert.AreEqual(8, result);
        }

        [Test]
        public void IsEven_EvenNumber_ReturnsTrue()
        {
            bool IsEven(int number) => number % 2 == 0;

            bool result = IsEven(10);
            Assert.IsTrue(result);
        }

        [Test]
        public void ToUpper_LowercaseInput_ReturnsUppercase()
        {
            string ToUpper(string input) => input.ToUpper();

            string result = ToUpper("hello");
            Assert.AreEqual("HELLO", result);
        }

        [Test]
        public void ContainsItem_ItemInList_ReturnsTrue()
        {
            bool ContainsItem(List<string> list, string item) => list.Contains(item);

            var fruits = new List<string> { "apple", "banana", "cherry" };
            bool result = ContainsItem(fruits, "banana");
            Assert.IsTrue(result);
        }

        [Test]
        public void Multiply_ValidInputs_ReturnsProduct()
        {
            int Multiply(int x, int y) => x * y;

            int result = Multiply(6, 7);
            Assert.AreEqual(42, result);
        }
    }
}