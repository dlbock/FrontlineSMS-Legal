describe('Console', function () {
    it("should allow logging to the global console object", function() {
       console.log("foo");
       expect("succeeded without exception").toBeTruthy();
    });
});