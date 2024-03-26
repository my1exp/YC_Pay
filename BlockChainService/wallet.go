package BlockChainService

type RequestWallet struct {
	Network  string `json:"network" binging:"required"`
	Currency string `json:"currency" binging:"required"`
}
